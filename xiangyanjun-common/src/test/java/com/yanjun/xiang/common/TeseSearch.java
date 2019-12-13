package com.yanjun.xiang.common;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeseSearch {

    @Autowired
    RestHighLevelClient client;
    
    @Autowired
    RestClient restClient;
    
    
    /**
     * 查询type下所有文档
     * 打印结果:
     *  {"studymodel":"201002","name":"Bootstrap开发"}
        {"studymodel":"201001","name":"java编程基础"}
        {"studymodel":"201001","name":"spring开发基础"}
    
       对应http请求json
       {
         "query": {
           "match_all": {}
         },
         "_source": ["name","studymodel"]
       }
           
     */
    @Test
    public void testSearchAll()throws Exception{
//        Settings settings = Settings.builder().put("cluster.name", "es-1").build();

//            new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.105"), 9300));
        GetIndexRequest request = new GetIndexRequest();
        request.indices("lib");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);

        GetRequest getRequest = new GetRequest("lib", "user", "3");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(getResponse));
        //1、构造sourceBuild(source源)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"}, new String[]{})
                           .query(QueryBuilders.matchAllQuery());
        //2、构造查询请求对象
        SearchRequest searchRequest = new SearchRequest("lib");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //3、client 执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        System.out.println(query.size());
        //4、打印结果
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * 分页查询type下所有文档
     * 
     * json 参数
     * {
     *   "from":0,
     *   "size":1,
     *   "query": {
     *      "match_all": {}
     *   },
     *   "_source": ["name","studymodel"]
     * }
     * 
     * 打印结果
     * {"studymodel":"201002","name":"Bootstrap开发"}
       {"studymodel":"201001","name":"java编程基础"}
     */
    @Test
    public void testSearchAllByPage()throws Exception{
        
        //1、构造sourceBuild
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"}, new String[]{})
                           .query(QueryBuilders.matchAllQuery())
                           .from(0).size(2);//分页查询,下表从0开始
        
        //2、构造searchRequest请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //3、client执行请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        
        //4、打印结果
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    /**
     * term query: 精确查询、在搜索是会精确匹配关键字、搜索关键字不分词
     * 
     * json 参数
     * {
     *   
     *   "query": {
     *      "term": {
     *          name: "spring"
     *      }
     *   },
     *   "_source": ["name","studymodel"]
     * }
     */
    @Test
    public void testTermQuery()throws Exception{
        
        //1、设置queryBuilder
        TermQueryBuilder termQueryBuild = QueryBuilders.termQuery("name","spring");
        
        //2、设置sourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termQueryBuild)//设置Term query查询
                           .fetchSource(new String[]{"name","studymodel"}, new String[]{});
        
        //3、构造searchRequest
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //4、client发出请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        
        //5、打印结果
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * 根据id精确查询:根据提供的多个id去匹配
     * 
     * json 参数
     * {
     *   query{
             "ids": {
                "type": "doc",
                "values": ["TeH_2WcBH5cUK","TuEB2mcBf3IfcTiHccWJ"]
             }
     *   },
     *   "_source": ["name","studymodel"]
     * }
     */
    @Test
    public void testIdsQuery()throws Exception{
        
        //1、够着queryBuild
        //构造idList,注意数组每个元素必须是一个完整的id能匹配的上,第一条没有记录匹配上，第二条中
        String[] idList = new String[]{"TeH_2WcBH5cUK","TuEB2mcBf3IfcTiHccWJ"};
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("_id",idList);//特别注意用termsQuery,不要用termQuery
        
        //2、构造sourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termsQueryBuilder)
                           .fetchSource(new String[]{"name","studymodel"}, new String[]{});;
        
        //3、构造searchRequest
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //4、client执行
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        
        //5、打印结果
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * match Query就是全文检索,收缩方式就是先将搜索字符串分词、然后到索引分词列表去匹配
     * json 参数
     * {
     *   query{
     *     "match": {
     *         "descrition":{  //还是需要指定字段的，description是字段名
     *                 "query": "世界第一",
     *              "operate": "or"  //or表示分词之后，只要有一个匹配即可,and表示分词在文档中都匹配才行
     *         }
     *      },
     *      "_source": ["name","studymodel"]
     *   }
     * }
     */
    @Test
    public void testmatchQuery()throws Exception{
        
        //queryBuild
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "世界第一");
        
        //searchSorcebuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder)
                           .fetchSource(new String[]{"name","studymodel"}, new String[]{});
        
        
        //searchRequest
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //client->search
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        //print end
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    /**
     * minimum_should_match:
     *   or只能表示只要匹配一个即可、minimum_should_match可以指定文档匹配词的占比,注意这个占比的基数是搜索字符串分词的个数
     * json 参数
     * {
     *   query{
     *     "match": {
     *         "descrition":{  //还是需要指定字段的，description是字段名
     *                 "query": "spring开发",
     *              "minimun_should_match": "80%"
     *         }
     *      },
     *      "_source": ["name","studymodel"]
     *   }
     * }
     */
    @Test
    public void testMinimumShouldMatchQuery()throws Exception{
        
        //queryBuild
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "世界第一")
                     .minimumShouldMatch("80%");
        
        
        //searchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder)
                           .fetchSource(new String[]{"name","studymodel"}, new String[]{});
        
        //searchRequest
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    /**
     * multi_match Query:
     *   用于一次匹配多个File进行全文检索、前面match都是一个Field
     *   多个字段可以通过提升boost(权重),来提高得分,实现排序靠前
     *   
     * json 参数
     * {
     *   query{
     *     "multi_match": {
     *         "query": "spring css", //搜索字符串
     *         "minimum_should_match": "50%",
     *         "fields": ["name^10","description"] //设置匹配name 和 description字段,将boost的boost提10倍
     *      }
     *   }
     * }
     */
    @Test
    public void testMultiMatchQuery()throws Exception{
        
        
        //queryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("Spring框架","name","description")
                                                                .minimumShouldMatch("50%")//设置百分比
                                                                .field("name", 10);//提升boost
        //searchSourceBuild
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder); 
        
        //searchRequest
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(searchSourceBuilder);
        //search
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    /**
     * 布尔查询
     *   对应lucene的Boolean查询、实现将多个查询条件结合起来
     *   三个参数：
     *      must:只有符合所有查询的文档才被查询出来,相当于AND
     *      should:至少符合其中一个,相当于OR
     *      must_not:不能符合任意查询条件,相当于NOT
     *      
     * json 参数
     * {
     *   "_source": ["name","pic"],
     *   "from": 0,
     *   "size": 1,
     *   query{
     *     bool:{
     *      must: [
     *         {
     *           "multi_match": {
     *             "query": "spring框架",
     *             "minimum_should_match": "50%",
     *             "fields": ["name^10","description"]
     *           }
     *         },{
     *           "term": {
     *             "studymodel": "201001"
     *           }
     *         }
     *      ]
     *    }  
     *   }
     * }
     */
    @Test
    public void testBooleanQuery()throws Exception{
        
        //1、够着QueryBuild
        
        //构造multiQureyBuilder
        MultiMatchQueryBuilder multiQueryBuilder = QueryBuilders.multiMatchQuery("Spring框架","name","description")
                                                                .minimumShouldMatch("50%")//设置百分比
                                                                .field("name", 10);
         //构造termQueryBuilder
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        
        //构造booleanQueryBuilder
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                     .must(multiQueryBuilder)
                     .must(termQueryBuilder);
        
        //2、构造查询源
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.fetchSource(new String[]{"name","pic"}, new String[]{});
        ssb.query(boolQueryBuilder);
        
        //3、构造请求对象查询
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        searchRequest.source(ssb);
        
        //4、client执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * 过滤器:
     *   过滤器判断的是文档是否匹配，不去计算和判断文档的匹配度得分,所以过滤器性能比查询高、方便缓存
     *   推荐尽量使用过滤器、或则过滤器搭配查询使用
     *   过滤器使用的前提是bool查询
     *   过滤器可以单独使用,但是不能提点multi Query, 因为过滤器每个Query都是单字段过滤
     *      
     * json 参数
     * {
     *   "_source": ["name","pic"],
     *   "from": 0,
     *   "size": 1,
     *   query{
     *     bool:{
     *      must: [
     *         {
     *           "multi_match": {
     *             "query": "spring框架",
     *             "minimum_should_match": "50%",
     *             "fields": ["name^10","description"]
     *           }
     *         }
     *      ],
     *      fileter: [
     *         {
     *            term: {"studymodel": "21001"} //针对字段进行过滤
     *         },{
     *            range: {  //针对范围进行过滤
     *              "price": {"gte":60,"lte":100}
     *            }
     *         }
     *      ]
     *    }  
     *   }
     * }
     */
    @Test
    public void testFileter()throws Exception{
        
        //1、构造QueryBuild
        
        //构造multiQureyBuilder
        MultiMatchQueryBuilder multiQueryBuilder = QueryBuilders.multiMatchQuery("Spring框架","name","description")
                                                                .minimumShouldMatch("50%")//设置百分比
                                                                .field("name", 10);
        
        //构造booleanQueryBuilder
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                     .must(multiQueryBuilder);
        
        //过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"))
                        .filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
        
        //2、构造查询源
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.fetchSource(new String[]{"name","pic"}, new String[]{});
        ssb.query(boolQueryBuilder);
        
        //3、构造请求对象查询
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        searchRequest.source(ssb);
        
        //4、client执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * 排序:
     * 可以设置排序字段对查询结果进行排序
     * keyword 、date 、float 等可以加
     * 注意text 不能加
     *      
     * json 参数
     * {
     *   "_source": ["name","pic","description","price],
     *   query{
     *     bool:{
     *      fileter: [ //过滤器也可以单独使用,但是只能用于单个字段
     *         {
     *            term: {"studymodel": "21001"} //针对字段进行过滤
     *         },{
     *            range: {  //针对范围进行过滤
     *              "price": {"gte":60,"lte":100}
     *            }
     *         }
     *      ]
     *    }  
     *   },
     *   "sort": [
     *     {
     *       "studymodel": "desc"
     *     },{
     *       "price": "asc"
     *     }
     *   ]
     * }
     */
    @Test
    public void testSort()throws Exception{
        
        //1、构造QueryBuild
        
        //构造booleanQueryBuilder
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        
        //过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"))
                        .filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
        
        //2、构造查询源
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.fetchSource(new String[]{"name","pic","studymodel","price"}, new String[]{});
        ssb.query(boolQueryBuilder);
        ssb.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));
        ssb.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        
        //3、构造请求对象查询
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        searchRequest.source(ssb);
        
        //4、client执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
    
    
    /**
     * 高亮显示:
     * 将搜索结果中的一个或多个字突出显示，以便向用户展示匹配的关键字的位置
     *      
     * json 参数
     */
    @Test
    public void testHighlight()throws Exception{
        
        //1、构造QueryBuild
        
        MultiMatchQueryBuilder multiQueryBuilder = QueryBuilders.multiMatchQuery("开发框架", "name","description")
                     .field("name", 10)
                     .minimumShouldMatch("50%");
        
        //构造booleanQueryBuilder
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                                                         .must(multiQueryBuilder);
        
        //过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"))
                        .filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
        
        //2、设置高亮
        //设置标签        
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>")//设置签缀
                        .postTags("</tag>");//设置后缀
        //设置高亮字段
        highlightBuilder.fields().add(new Field("name"));
        highlightBuilder.fields().add(new Field("description"));
        
        
        //3、构造查询源
          SearchSourceBuilder ssb = new SearchSourceBuilder();
          ssb.fetchSource(new String[]{"name","pic","studymodel","price"}, new String[]{})
             .query(boolQueryBuilder)
             .sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC))
             .sort(new FieldSortBuilder("price").order(SortOrder.ASC))
           .highlighter(highlightBuilder);
          
        //4、构造请求对象查询
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc")
                     .source(ssb);
        
        //5、client执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //6、取出高亮字段
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getHighlightFields());
        }
    }
    
    
    
    
    
}