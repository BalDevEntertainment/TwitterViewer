# TwitterViewer

## [IMPORTANT]

Create a file called secrets.properties with the following structure:

TWITTER_API_KEY="your_base_64_encoded_key"

This is to avoid publishing the api key on a repo.


## MVP: Minimal viable product

### [Finished]

**- Support rotation of the phone without loosing any data on screen**

_Used setRetainInstance()_

**- Even though it sounds unnecessary; use a Fragment for the UI**

**- Show the list of tweets listed by the based on the search term**

**- Have the app support Android OS 2.3 and up (API 10)**

_Tested on Google Nexus One - 2.3.7 - API 10 - 480x800_

**- Third party libraries.**

_1. Butterknife_

_2. Dagger2_

_3. Retrofit_

_4. GSON_

_5. RxJava_

_6. Fresco_

_7. RetroLambda_
    
**- You cannot use any Android Twitter SDK**

_Used Retrofit with the Rest API provided by twitter._

### Optional

**- Show the first image associated to a tweet**
