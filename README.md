# NearbyMeApp

## Set-up Facebook Login.
- Create a new Facebook App
- Copy the app credentials(fb_login_protocol_scheme, facebook_app_id) into their respective slots in strings.xml
- Add the HashKeys for the device
  - Inside LoginActivity. There is a commented method: printHashKey()
  - If this method is uncommented and run inside onCreate() it will print the current device HashKey
  - Add the printed hash to the facebook App.
