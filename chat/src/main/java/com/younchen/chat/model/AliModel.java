package com.younchen.chat.model;

/**
 * Created by younchen on 2015/11/19.
 */
public class AliModel {
//    OSSService ossService;
//    OSSFederationToken token;
//    EventBus bus=EventBus.getDefault();
//
//    public void init(final Context appContext) {
//
//        ClientApi.getAliToken(new ResultCallBack<OSSFederationToken>() {
//            @Override
//            public void onError(Request request, HttpException httpException) {
//
//                ToastUtil.show(httpException.getMessage());
//            }
//
//            @Override
//            public void onResponse(OSSFederationToken response) {
//
//                token = response;
//                ossService = OSSServiceProvider.getService();
//                ossService.setApplicationContext(appContext);
//                ossService.setGlobalDefaultHostId("oss-cn-hangzhou.aliyuncs.com");
//                ossService.setAuthenticationType(AuthenticationType.FEDERATION_TOKEN); // 设置加签方式为STS FederToken鉴权方式
//                ossService.setGlobalDefaultStsTokenGetter(new StsTokenGetter() {
//                    @Override
//                    public OSSFederationToken getFederationToken() {
//                        return token;
//                    }
//                    // 在这里编写您的代码，实现获取一个新的STS Token
//                    // 一般情况下，这个Token应该是通过网络请求去到您的业务服务器获取
//                    // 注意，返回的OSSFederationToken必须包含有效的四个字段：tempAK/tempSK/securityToken/expiration
//                    // expiration值为Token的失效时间，格式为UNIX Epoch时间，即从协调世界时1970年1月1日0时0分0秒起到Token失效时刻的总秒数
//                });
//                ClientConfiguration conf = new ClientConfiguration();
//                conf.setConnectTimeout(15 * 1000); // 设置建连超时时间，默认为30s
//                conf.setSocketTimeout(15 * 1000); // 设置socket超时时间，默认为30s
//                conf.setMaxConcurrentTaskNum(10); // 设置全局最大并发任务数，默认10个
////                ossService.setClientConfiguration(conf);
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        List<ListObjectResult.ObjectInfo> list =  getBucket();
////                        bus.post(list);
////                    }
////                }).start();
//
//            }
//        });
//    }
//
//    public List<ListObjectResult.ObjectInfo> getBucket() {
//        OSSBucket sampleBucket = ossService.getOssBucket("moyiza");
//        sampleBucket.setBucketACL(AccessControlList.PUBLIC_READ_WRITE);
//        sampleBucket.setBucketHostId("oss-cn-beijing.aliyuncs.com");
//
//        ListObjectOption opt = new ListObjectOption();
//        opt.setMaxKeys(500);
//        opt.setMarker("prefix187");
//        ListObjectResult result = null; // 调用接口罗列Bucket中的Objects
//        try {
//            result = sampleBucket.listObjectsInBucket(opt);
//        } catch (OSSException e) {
//            e.printStackTrace();
//        }
//
//        int objectNum = result.getObjectInfoList().size();
//        String nextMarkder = result.getNextMarker();
//        Boolean isTruncated = result.isTruncated();
//        List<ListObjectResult.ObjectInfo> objectList = result.getObjectInfoList(); // 获取返回结果中的Objects列表
//        List<String> commonPrefixes = result.getCommonPrefixList(); // 在设置了Delimiter时，有相同前缀的object会被分为一组，记录在返回的commonprefix中
//
//        return objectList;
//    }
}
