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

                    System.out.println("MSG: " + message);
                    if (message.toLowerCase().contains("userid")) {
                        String token = message.substring(message.indexOf("token") + 10, message.indexOf("token") + 10 + 43);
                        System.out.println("token: " + token);
                        userToken[0] = token;

                    }

                    if (message.contains("stream-room-messages")) {
                        String userName = message.substring(message.indexOf("username") + 13, message.lastIndexOf("name") -5);
                        System.out.println("UserName: " + userName);

                        if (!userName.startsWith("guest")) {

                            String responseMessage = message.substring(message.lastIndexOf("msg") + 8, message.indexOf("ts")-5);
                            System.out.println("Response MSG: " + responseMessage);
                        }

                    }


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


            String loginMessage = "[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"livechat:registerGuest\\\",\\\"params\\\":[{\\\"token\\\":\\\"" + websocketToken + "\\\",\\\"name\\\":\\\"" + clientName + "\\\",\\\"email\\\":\\\"yyyyyy@yyyyy.com\\\"}],\\\"id\\\":\\\"5\\\"}\"]";
            System.out.println(loginMessage);
            clientEndPoint.sendMessage(loginMessage);
            Thread.sleep(5000);


            String finalLoginTokenMessage = "[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"login\\\",\\\"params\\\":[{\\\"resume\\\":\\\"" + userToken[0] + "\\\"}],\\\"id\\\":\\\"6\\\"}\"]";
            System.out.println(finalLoginTokenMessage);
            clientEndPoint.sendMessage(finalLoginTokenMessage);
            Thread.sleep(5000);

            clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"sendMessageLivechat\\\",\\\"params\\\":[{\\\"_id\\\":\\\"" + messageId + "\\\",\\\"rid\\\":\\\"" + roomId + "\\\",\\\"msg\\\":\\\"hi hamin\\\",\\\"token\\\":\\\"" + websocketToken + "\\\"}],\\\"id\\\":\\\"8\\\"}\"]");
            Thread.sleep(5000);


            //["{\"msg\":\"sub\",\"id\":\"bxGv6Z2YAnu7pPLTR\",\"name\":\"stream-notify-room\",\"params\":[\"iALtWj53jbv5LjXvq/typing\",false]}"]

            String newId = randomGenerator.randomString(17);
            String subRequest = "[\"{\\\"msg\\\":\\\"sub\\\",\\\"id\\\":\\\"" + newId + "\\\",\\\"name\\\":\\\"stream-notify-room\\\",\\\"params\\\":[\\\"" + roomId + "/typing\\\",false]}\"]";
            System.out.println("zero: " + subRequest);
            clientEndPoint.sendMessage(subRequest);
            Thread.sleep(5000);

            newId = randomGenerator.randomString(17);
            subRequest = "[\"{\\\"msg\\\":\\\"sub\\\",\\\"id\\\":\\\"" + newId + "\\\",\\\"name\\\":\\\"stream-room-messages\\\",\\\"params\\\":[\\\"" + roomId + "\\\",false]}\"]";
            System.out.println("first: " + subRequest);
            clientEndPoint.sendMessage(subRequest);
            Thread.sleep(5000);


            newId = randomGenerator.randomString(17);
            subRequest = "[\"{\\\"msg\\\":\\\"sub\\\",\\\"id\\\":\\\"" + newId + "\\\",\\\"name\\\":\\\"stream-livechat-room\\\",\\\"params\\\":[\\\"" + roomId + "\\\",true]}\"]";
            System.out.println("Second: " + subRequest);
            clientEndPoint.sendMessage(subRequest);
            Thread.sleep(5000);


            newId = randomGenerator.randomString(17);
            subRequest = "[\"{\\\"msg\\\":\\\"sub\\\",\\\"id\\\":\\\"" + newId + "\\\",\\\"name\\\":\\\"stream-notify-room\\\",\\\"params\\\":[\\\"" + roomId + "/typing\\\",true]}\"]";
            System.out.println("Third: " + subRequest);
            clientEndPoint.sendMessage(subRequest);
            Thread.sleep(1000);




            while (true) {

                messageId = randomGenerator.randomString(7);
                clientEndPoint.sendMessage("[\"{\\\"msg\\\":\\\"method\\\",\\\"method\\\":\\\"sendMessageLivechat\\\",\\\"params\\\":[{\\\"_id\\\":\\\"" + messageId + "\\\",\\\"rid\\\":\\\"" + roomId + "\\\",\\\"msg\\\":\\\"hi hamin\\\",\\\"token\\\":\\\"" + websocketToken + "\\\"}],\\\"id\\\":\\\"8\\\"}\"]");
                Thread.sleep(3000);

            }


        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}