# 1. 初始化MySQL数据库
导入数据库文件[oauth2.sql](oauth2.sql)

# 2. 修改MySQL数据库连接
```yaml
spring:
  datasource:
    password: 111111
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/oauth2
```

# 3. 启动工程
占用9000端口

# 4. OAuth2各种授权类型验证
测试的代码位于 [src/test/java/example/AuthorizationTest.java](src/test/java/example/AuthorizationTest.java)
## 4.1 客户端模式
```java
void testClient() throws IOException {
    HttpPost httpPost = createHttpPost();
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.RESPONSE_TYPE,"client_credentials"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
    //{"access_token":"eyJraWQiOiI5N2JiN2FiMC1jYTgxLTRjODgtYjFjYS01OTJiZGYyMjdiNzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY0NzI3MzIwNiwic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOlwvXC9hdXRoLXNlcnZlcjo5MDAwIiwiZXhwIjoxNjQ3MjczNTA2LCJpYXQiOjE2NDcyNzMyMDZ9.O7zKZ8PiVdnfUlKmbwJoBg5LAmo6-N6L420lKp05JdSOQkhLjoRvnyggDa1qh2J6HzuD9um7-mPSCZXqOwCojqyx6mpiBP3Hdbz6uug4fdtzdWv78peW8plVtoNz8c8-8v0dxIHkvCWBdnLCPPROdclCXMUHLWSXusW7B2bR6CFp_MXRMoxUP7MuGbgElytmhpxkqx4Vfc0NOJgf8E3-PwBwHb6-heMeRFekdvSUmMZgFHgTcxSv95grlw-Gf4qAKYOCQP4ao2UA_YyfEGMS5CgdQmMjIpdHpgO9U2lfmLRunHqnnro6zpX6TO1I4JbJnOVy-0pig6vC1uMyb_gzNw","scope":"openid message.read message.write","token_type":"Bearer","expires_in":299}
}
```
## 4.2 密码模式(自行扩展实现)
```java
void testPassword() throws IOException {
    HttpPost httpPost = createHttpPost();
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.RESPONSE_TYPE,"password"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.USERNAME,"user1"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.PASSWORD,"password"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
    //{"access_token":"eyJraWQiOiI5N2JiN2FiMC1jYTgxLTRjODgtYjFjYS01OTJiZGYyMjdiNzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDcyNzMyMzEsInNjb3BlIjpbIm9wZW5pZCIsIm1lc3NhZ2UucmVhZCIsIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzI3MzUzMSwiaWF0IjoxNjQ3MjczMjMxfQ.fwT2VynDthPG-AlPeQaAD_5HusKkhmNLt23NNwZ25fSndnO2Wjw78RzGK3IZJFGfUJHhcKOU_q4gOjC8Uff0UpQ6GqaxY4ex2GGod09RNYqZPdQODJFTM_OBxsgjZdEExKJS6K-4qD-QDIMZ4JsgTnWu_ERjhMiS1OfSjx2q1jLNpelqDRl2pHvCZKsk8Ey1QaTKZeB_fck8AOrICD5DWPC7MnN2DTBac0dYc_3HOan2IW5DN2g7XkdgH8cvn0w_EN70HysC1-AHuV4Lu4rQL8ijpfEfEUejpQxlUeMCJkyBji5RnFaHlSrn07iewzyp43-egmzgoKmBaeh16SpACA","refresh_token":"BcWkO5Yu4UL1sk8JpUpABz0JnRjk7E-Vfik4smWlYjU9yWjqo6VVqyeUEHPwu5dm6h8MAi9YGR-m4i5GizzN9k_Kn73iLSfbuIl-247PU164pZl2mC1UFBZVdXYw2IKe","sub":"user1","aud":["messaging-client"],"scope":"openid message.read message.write","iss":"http://auth-server:9000","token_type":"Bearer","expires_in":300}
}
```
## 4.3 授权码模式
```java
void testCode() throws IOException {


    String redirectUrl = "http://www.baidu.com";
    String url = ENDPOINT+ UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, "code")
            .queryParam(OAuth2ParameterNames.CLIENT_ID, "messaging-client")
//                .queryParam("scope", "openid")
            .queryParam(OAuth2ParameterNames.STATE, "some-state")
            .queryParam(OAuth2ParameterNames.REDIRECT_URI, redirectUrl)
            .toUriString();
    System.out.println(url);
    //浏览器访问 http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&state=some-state&scope=message.write&redirect_uri=http://www.baidu.com
    HttpPost httpPost = createHttpPost();
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"authorization_code"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.CODE,"GTK_ltZuH5KqWhLZtUvrA9ismRZ3wCHyQCE5Uxk9-3MMXc_r4y97g2-UEZa7bnwSTj-e40z9SdVmULZfdofKeQoUfOgY67JtSayaUaPzCutHDmG2vr7feiDIMHC8EWUV"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.STATE,"some-state"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.REDIRECT_URI,"http://www.baidu.com"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);

    //{"access_token":"eyJraWQiOiJmMmNkOGRiNC00ZGQwLTQ5NGEtOWFjNi00MTk4YjhjZDYyOGMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc0NDE3OTEsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzQ0MjA5MSwiaWF0IjoxNjQ3NDQxNzkxfQ.qXJj5YsoOsUYNTqvgtb02BW9S2r_GAzB2ckEMuLKpCsQ18p5dkgVO_AFRd4PbKjJ0ACcuu6-bTyp4fmED6NMqhVjv8it7roqa5A0iGkFT5Le9E8uwvZRAfDMbYdH9GbvOq4OxJXmrHKBCvBRssV5kgG4t41EXvejspk5prVCXQZUVWZEpfMWDcKW8un7GsY0EqbwV74FZ800gGa_WbSO0K5p8G90BGzTad21mpCpnT-1TC01CQdWik2ig9joeANgojify5tAJeHPH7SejyiNJnjUbkLobgqcmb-ZvjxkVsB5ZQcJp7hiC6GxOD7b3E5sL8tRezeD88nZzPknBfxvBA","refresh_token":"leiALygiLS6maf0aCOxwyp77BxqZw3kGy8XvRCzX-9alDhldpI0f8nzza1wWSBtK6jMYfb2YDv-bPBdbi9r9wbXeqtPoguoxqXLwqk-4x6H_v_9me17yWcyUnrwf6WhB","scope":"message.write","token_type":"Bearer","expires_in":300}
}
```
## 4.4 刷新token
```java
void testRefresh() throws IOException {
    HttpPost httpPost = createHttpPost();
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.REFRESH_TOKEN.getValue()));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.REFRESH_TOKEN,"69t9Ebwp7araMoTXChxtS5OD6jG5FPAwm2Opx0TA3ruSYEJp5fThcS7l0J2-8PwfQMwzXighBURHORt_aEdVpK7PFIB42DRu_D8i4KNAflAdqYJY94IvacYcnX96uNV3"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
    //重新生成access_token令牌，refresh_token的值不变
}
```

## 4.5 PKCE模式
PKCE模式是授权码模式的增强版本，属于OAuth2.1新增的内容，主要是解决开放客户端授权回调被恶意拦截的问题，比授权码模式更加安全，另外PKCE模式提供了对app端以及单页面应用（Single Page Application）的支持，在此模式下，不需要传递client_secret
```java
//PKCE模式1：在code模式基础上添加pkce参数，其他所有参数和传参方法不变
void testPkce() throws IOException {
    String redirectUrl = "http://www.baidu.com";
    String url = ENDPOINT+ UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, "code")
            .queryParam(OAuth2ParameterNames.CLIENT_ID, "messaging-client")
//                .queryParam("scope", "openid")
            .queryParam(OAuth2ParameterNames.STATE, "some-state")
            .queryParam(OAuth2ParameterNames.REDIRECT_URI, redirectUrl)
            .queryParam(PkceParameterNames.CODE_CHALLENGE,"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE")
            .queryParam(PkceParameterNames.CODE_CHALLENGE_METHOD,"S256")
            .toUriString();
    System.out.println(url);
    //浏览器访问 http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&state=some-state&scope=message.write&code_challenge_method=S256&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&redirect_uri=http://www.baidu.com
    HttpPost httpPost = createHttpPost();
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"authorization_code"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.CODE,"8LhY5lGoXsM991tXLbvew5bx-jN0T_zN-IhYB-qPUCZRRiz6jpw_g2aILuonEskazxS2qowQzkZDEtH0PPrPDwUBwYKhpsTwXadm2k-PnuJMSe7GQqd5jKuh9vgofhxi"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.STATE,"some-state"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.REDIRECT_URI,"http://www.baidu.com"));
    params.add(new BasicNameValuePair(PkceParameterNames.CODE_VERIFIER,"SWv.9QcLNnTC9i5qYN_p_T4tH0y6mlJZb71VU.FrLHvgC1UTwWTEVWb8zIjuR2OZC2i6HN3A_5qI9hzAHJ4JLIU6lZLI0pb1ugn8X1nx8sxQ21DYmpLBW5uj2Cdj~AlF"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
    // {"access_token":"eyJraWQiOiI2N2E0YTBhZC00N2I5LTRiN2EtOTZkYy1jZTNhOGFiMjA5ODEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc1ODM5ODYsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzU4NDI4NiwiaWF0IjoxNjQ3NTgzOTg2fQ.MYycfMpkOR2gyWIGoepeL0H3U_nvLy7uFW80Nu76fztynYa9wxLw4swEqwXuidlK5YO7oyyNR5WOILyCJ9uQamlj8xvmeSOp_QxSZQzygm2-RNbBVw5EjokCZ2j8axW6gCMQNf7PePFMEkM047l5d7PvQDfzhO5ept5_HjXcjQ5zwLVDTcjYfvY9vcROwzrhwEHETOgRDB_DX5iUuA25IlIa_qWR7qI1li7ClMrf9pxldHV5KTCry2NDRxC3GVw17GA58w-Lb4F6Atgwl9Pl3hz82U45HvIYN3R-43w10jbZRjMr1K90ZDRZq1QyvFci7ehSVxAD_PtVm05v1ZoZiQ","refresh_token":"doDkhiBrVvv-0_J8q2rhwUAmqhw2tgtjAZ4LUUgqSdRaP9W4PsmjJOI6BdJ1S6A_fQqEzYtTd9c_uTX58xBvE4y1qedUoXy0oMT_Z3X7iQ6wpc7sFdyoBjqhI_odgeKX","scope":"message.write","token_type":"Bearer","expires_in":300}
}
```
```java
// PKCE模式2，不需要传递client_secret,该模式下，client_id必须通过client_secret_post方法传递，Basic 模式下通过header传递不可行
void testPkce2() throws IOException {
    String redirectUrl = "http://www.baidu.com";
    String url = ENDPOINT+ UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, "code")
            .queryParam(OAuth2ParameterNames.CLIENT_ID, "messaging-client")
//                .queryParam("scope", "openid")
            .queryParam(OAuth2ParameterNames.STATE, "some-state")
            .queryParam(OAuth2ParameterNames.REDIRECT_URI, redirectUrl)
            .queryParam(PkceParameterNames.CODE_CHALLENGE,"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE")
            .queryParam(PkceParameterNames.CODE_CHALLENGE_METHOD,"S256")
            .toUriString();
    System.out.println(url);
    //浏览器访问 http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&state=some-state&scope=message.write&code_challenge_method=S256&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&redirect_uri=http://www.baidu.com
//        HttpPost httpPost = createHttpPost("messaging-client",null);
    HttpPost httpPost = new HttpPost(TOKEN_ENDPOINT);
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair("client_id","messaging-client"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"authorization_code"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.CODE,"1SfS3cFSfIonVRCCWIKdatjYuIXu671tAyhs_ItgO0izssFDi7ZtA4yQRwZORpu5aXRAfKItuyLCNbbAZedJEFrwEIN3P3xkEWVdQyt_MisHzwvhHTEzEL5ZkdDBo36b"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.STATE,"some-state"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.REDIRECT_URI,"http://www.baidu.com"));
    params.add(new BasicNameValuePair(PkceParameterNames.CODE_VERIFIER,"SWv.9QcLNnTC9i5qYN_p_T4tH0y6mlJZb71VU.FrLHvgC1UTwWTEVWb8zIjuR2OZC2i6HN3A_5qI9hzAHJ4JLIU6lZLI0pb1ugn8X1nx8sxQ21DYmpLBW5uj2Cdj~AlF"));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
    // {"access_token":"eyJraWQiOiI2N2E0YTBhZC00N2I5LTRiN2EtOTZkYy1jZTNhOGFiMjA5ODEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc1ODM5ODYsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzU4NDI4NiwiaWF0IjoxNjQ3NTgzOTg2fQ.MYycfMpkOR2gyWIGoepeL0H3U_nvLy7uFW80Nu76fztynYa9wxLw4swEqwXuidlK5YO7oyyNR5WOILyCJ9uQamlj8xvmeSOp_QxSZQzygm2-RNbBVw5EjokCZ2j8axW6gCMQNf7PePFMEkM047l5d7PvQDfzhO5ept5_HjXcjQ5zwLVDTcjYfvY9vcROwzrhwEHETOgRDB_DX5iUuA25IlIa_qWR7qI1li7ClMrf9pxldHV5KTCry2NDRxC3GVw17GA58w-Lb4F6Atgwl9Pl3hz82U45HvIYN3R-43w10jbZRjMr1K90ZDRZq1QyvFci7ehSVxAD_PtVm05v1ZoZiQ","refresh_token":"doDkhiBrVvv-0_J8q2rhwUAmqhw2tgtjAZ4LUUgqSdRaP9W4PsmjJOI6BdJ1S6A_fQqEzYtTd9c_uTX58xBvE4y1qedUoXy0oMT_Z3X7iQ6wpc7sFdyoBjqhI_odgeKX","scope":"message.write","token_type":"Bearer","expires_in":300}
}
```

## 4.6 撤销token
撤销token需要传递client_id,client_secret和token参数
请求地址："/oauth2/revoke"
撤销成功后返回200状态码
```java
void testRevoke() throws IOException {
    String accessToken = "eyJraWQiOiI3NjI0NDI1ZC0wYjAxLTQwNjctYTVlYS0zZmZiNmE2OWMwMzAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc3NDM4MzQsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0Nzc0NDEzNCwiaWF0IjoxNjQ3NzQzODM0fQ.Ji3KIx53oA9dzu5anFPqNbjYQvEVayaVVJ4CRgGyUONXDS1k_92m3ikS2Ckzs0uaGjy7eLrPWUuCcNUhPSM0-CWQjiaaGaqag6inb49oET9Ul7OA202gLoMidx-1q2WSLu8qUekELy619HPwN4B_OsHA5v1ZKx5h8g8gefDKhJB3gEMiNZgIhTd3WKTYxyJUF1uj39kWcxsNW5PnwbkfwMILKCGsHd8CLjE7OBIrudPnA5hqCc96--_gJZuuDIRm4ZpcGlK1Zl5TozM4uuBQGMtbLSdjOJ9w3nT5ugphTc4W9g70_g50SZI6A7zKmudfmuEZC0Qa7c4DG8jzMG99sg";
    HttpPost httpPost = new HttpPost(REVOKE_TOKEN_ENDPOINT);
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair(OAuth2ParameterNames.CLIENT_ID,"messaging-client"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.CLIENT_SECRET,"secret"));
    params.add(new BasicNameValuePair(OAuth2ParameterNames.TOKEN,accessToken));
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    execute(httpPost);
}
```

# 5. 功能扩展
## 5.1 新建客户端OAuth2Client信息
每次新增一个client都需要往如下数据库表中添加以下记录
### oauth2_token_setting表
```shell
settings.token.reuse-refresh-tokens=true
settings.token.id-token-signature-algorithm=RS256
settings.token.access-token-time-to-live=300 #秒
settings.token.refresh-token-time-to-live=3600 #秒
settings.token.access-token-format=self-contained #jwt自包含业务信息
```
### oauth2_client_setting
```shell
settings.client.require-proof-key=false
settings.client.require-authorization-consent=true
```
### oauth2_client_authorization_grant_type
```shell
# 选择以下一个或多个
authorization_code
refresh_token
client_credentials
password
```
### oauth2_client_authentication_scope
根据实际填写
### oauth2_client_authentication_method
表示对客户端认证的方法，可以是下列表中的一个或多个
```shell
client_secret_basic
basic #已经废弃，改成client_secret_basic
post  #已经废弃，改成使用client_secret_post
client_secret_post
client_secret_jwt
private_key_jwt
none
```
## 5.2 调用远程接口