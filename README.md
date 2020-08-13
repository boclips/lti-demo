# lti-demo
For people to be able to try out those meaty LTI features without having to use shady 3rd party apps

### Running LTI demo locally

To run LTI Demo against your local LTI repo, first run in `lti-demo`:

```
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

Next, naviagate to your LTI repo and comment out this line from the `InitiateLogin` method in `InitiateLoginController`:

```
@URL(protocol = "https", message = "'iss' parameter must be a valid HTTPS URL")
```

Finally, run `./gradlew bootRunLocal` in LTI and in the browser navigate to `localhost:8080` (you may have to delete
cookies or open in incognito if you already have a cookie saved from running the dev server)
