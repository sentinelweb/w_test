# w_test

* Uses kotlin coroutines for concurrency (need restructuring for proper testing)
* Retrofit for networking
* MVP pattern (V/P Separated by Contract interface)
* ViewModel handles configuration changes - stored in the view since it's lifecycle aware

Some Improvements:
* Restructure co-routine's execution in Presenterfor better testing
* Interactor for api calls (don't just execute in presenter) - this might be the fix for testability too
* Don't use domain class in view (make a model object for cake and map domain -> model object)
* Use `DefaultItemAnimator` for better `RecyclerView` animations

