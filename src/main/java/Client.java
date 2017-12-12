import java.net.URI;
import java.net.URISyntaxException;

public class Client {


    public static void main(String[] args) {
        try {

            RandomGenerator randomGenerator = new RandomGenerator();
            String roomId = randomGenerator.randomString(7);
            String messageId = randomGenerator.randomString(7);
            String clientName = randomGenerator.randomString(5);
            String websocketToken = "2nPrjeWLtQR7tjrbT"; //randomGenerator.randomString(17);

            final String[] userToken = {""};

            final String websocketUrl = "wss://rocket-chat-manual.herokuapp.com/sockjs/647/lhvo2mn7/websocket";
            //final String websocketToken = "2oFvHdeLPAosRG5v6";

            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(websocketUrl));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {

                    /*
                    Handle the receiving messages from the server
                    */


                }
            });


            clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"connect\\\",\\\"version\\\":\\\"1\\\",\\\"support\\\":[\\\"1\\\",\\\"pre2\\\",\\\"pre1\\\"]}\"]");
            Thread.sleep(2000);

            clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"livechat:loginByToken\\\",\\\"params\\\":[\\\"" + websocketToken + "\\\"],\\\"id\\\":\\\"1\\\"}\"]");
            Thread.sleep(2000);

            clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"livechat:getInitialData\\\",\\\"params\\\":[\\\"" + websocketToken + "\\\"],\\\"id\\\":\\\"2\\\"}\"]");
            Thread.sleep(5000);


            clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"sub\\\",\\\"id\\\":\\\"QbeCY9swgK7txkC8w\\\",\\\"name\\\":\\\"meteor.loginServiceConfiguration\\\",\\\"params\\\":[]}\"]");
            Thread.sleep(5000);






            while (true) {

               // Try to keep the connection alive. very dangerous!
            }


        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
