# w_test

* uses kotlin coroutines for concurrency (need restructuring for proper testing)
* retrofit for networking
* MVP pattern (V/P Separated by Contract interface)

Some Improvements:
* Restructure co-routine's execution in Presenterfor better testing
* Interactor for api calls (do just execute in presenter)
* Dont use domain class in view (make a model object for cake and map domain -> model object)

