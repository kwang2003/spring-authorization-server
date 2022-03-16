package example;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

class AuthorizationTest {
    private HttpClient httpClient = HttpClients.createDefault();
    private static final String ENDPOINT = "http://localhost:9000";
    private static final String TOKEN_ENDPOINT = ENDPOINT+"/oauth2/token";
    private Gson gson = new Gson();

    private HttpPost createHttpPost(){
        String clientId = "messaging-client";
        String clientSecret = "secret";
        HttpPost httpPost = new HttpPost(TOKEN_ENDPOINT);
        httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic "+Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes())));
        return httpPost;
    }

    private void execute(HttpPost httpPost) throws IOException {
        HttpEntity entity = httpClient.execute(httpPost).getEntity();
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        System.out.println(json);
    }

    @Test
    @DisplayName("password模式")
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

    @Test
    @DisplayName("客户端模式")
    void testClient() throws IOException {
        HttpPost httpPost = createHttpPost();
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair(OAuth2ParameterNames.RESPONSE_TYPE,"client_credentials"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        execute(httpPost);
        //{"access_token":"eyJraWQiOiI5N2JiN2FiMC1jYTgxLTRjODgtYjFjYS01OTJiZGYyMjdiNzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY0NzI3MzIwNiwic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOlwvXC9hdXRoLXNlcnZlcjo5MDAwIiwiZXhwIjoxNjQ3MjczNTA2LCJpYXQiOjE2NDcyNzMyMDZ9.O7zKZ8PiVdnfUlKmbwJoBg5LAmo6-N6L420lKp05JdSOQkhLjoRvnyggDa1qh2J6HzuD9um7-mPSCZXqOwCojqyx6mpiBP3Hdbz6uug4fdtzdWv78peW8plVtoNz8c8-8v0dxIHkvCWBdnLCPPROdclCXMUHLWSXusW7B2bR6CFp_MXRMoxUP7MuGbgElytmhpxkqx4Vfc0NOJgf8E3-PwBwHb6-heMeRFekdvSUmMZgFHgTcxSv95grlw-Gf4qAKYOCQP4ao2UA_YyfEGMS5CgdQmMjIpdHpgO9U2lfmLRunHqnnro6zpX6TO1I4JbJnOVy-0pig6vC1uMyb_gzNw","scope":"openid message.read message.write","token_type":"Bearer","expires_in":299}
    }

    @Test
    @DisplayName("授权码模式")
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
}
