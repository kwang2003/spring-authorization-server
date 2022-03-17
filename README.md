# 初始化MySQL数据库
导入数据库文件[oauth2.sql](oauth2.sql)

# 修改MySQL数据库连接
```yaml
spring:
  datasource:
    password: 111111
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/oauth2
```

# 启动工程
占用9000端口

# OAuth2各种授权类型验证
测试的代码位于 [src/test/java/example/AuthorizationTest.java](src/test/java/example/AuthorizationTest.java)
## 客户端模式
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
## 密码模式(自行扩展实现)
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
## 授权码模式
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
## 刷新token
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