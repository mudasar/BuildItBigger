/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package uk.appinvent.buiditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import cuk.appinvent.jokeprovider.JokeSmith;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.buiditbigger.appinvent.uk",
    ownerName = "backend.buiditbigger.appinvent.uk",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "getAJoke")
    public JokeBean getAJoke(@Named("isPaid") boolean isPaid) {
        JokeBean response = new JokeBean();

        JokeSmith jokeSmith = new JokeSmith();


        String joke = isPaid ? jokeSmith.getPaidJoke() : jokeSmith.getFreeJoke();

        response.setData(joke);

        return response;
    }
}
