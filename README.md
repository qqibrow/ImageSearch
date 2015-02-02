# ImageSearch
Image Search Android Client

## Features
1. User can enter a search query that will display a grid of image results from the Google Image API.
2. User can click on "settings" which allows selection of advanced search options to filter results.
3. User can configure advanced search filters such as:
    * Size (small, medium, large, extra-large)
    * Color filter (black, blue, brown, gray, green, etc...)
    * Type (faces, photo, clip art, line art)
    * Site (espn.com)
4. Subsequent searches will have any filters applied to the search results.
5. User can tap on any image in results to see the image full-screen
6. User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
7. User can share an image to their friends or email it to themselves

## Implementation Details
1. [Actionbar Searchview](http://guides.codepath.com/android/Extended-ActionBar-Guide#adding-searchview-to-actionbar)
2. [Sharing Content with Intents](http://guides.codepath.com/android/Sharing-Content-with-Intents), [PicassoDrawable cannot be cast to BitmapDrawable](https://github.com/square/picasso/issues/38)
3. [Lightweight modal overlay as Advance Filter UI](http://guides.codepath.com/android/Using-DialogFragment)
4. [StaggeredGridView to show images](https://github.com/etsy/AndroidStaggeredGrid)

## Demo
[![ScreenShot](http://offers.square2marketing.com/hs-fs/hub/112139/file-571711539-jpg/images/demo-resized-600.jpg)](https://www.youtube.com/watch?v=CeqxJ5kKXN8)
