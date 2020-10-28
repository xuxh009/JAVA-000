import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * desc
 *
 * @author xuxh
 * @date 2020/10/28 22:37
 */
public class HttpTest {

    public static void main(String[] args) {
        String url = "http://localhost:8801";
        doHttpClientRequest(url);
    }

    public static void doHttpClientRequest(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();

            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            System.out.println("响应码：" + response.getStatusLine());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String content = explainHttpEntity(entity);
                System.out.println("响应内容：" + content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String explainHttpEntity(HttpEntity entity) throws IOException {
        InputStream inputStream = entity.getContent();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(bytes, 0, bufferedInputStream.available());
        return new String(bytes);
    }
}
