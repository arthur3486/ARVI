# ARVI - Autoplayable RecyclerView Items

> ***[ExoPlayer](https://github.com/google/ExoPlayer)-based Android library that makes the implementation of the autoplayable RecyclerView video items an easy task***

**ARVI** will enable you to make your feeds more interactive and appealing to your end users without the need to spend a lot of your valuable time on the implementation.

[ ![Download](https://img.shields.io/maven-central/v/com.arthurivanets.arvi/arvi.svg?label=Download) ](https://mvnrepository.com/search?q=com.arthurivanets.arvi)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
![](https://img.shields.io/badge/API-21%2B-green.svg?style=flat)
![](https://travis-ci.org/arthur3486/ARVI.svg?branch=master)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ARVI-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7455)

## Contents

* [Demo](#demo-youtube)
* [Getting Started](#getting-started)
* [Basic Implementation](#basic-implementation)
* [Adapster-based Implementation](#adapster-based-implementation)
* [Advanced Use Cases](#advanced-use-cases)
* [Contribution](#contribution)
* [Hall of Fame](#hall-of-fame)
* [License](#license)

## Demo (YouTube)

[![YouTube Video](https://github.com/arthur3486/ARVI/blob/master/preview_image_1.png)](https://www.youtube.com/watch?v=q4SrpeyW7p8)

## Getting Started

### Prerequisites

**1. Make sure that you've added the `jcenter()` repository to your top-level `build.gradle` file.**

````groovy
buildscript {
    //...
    repositories {
        //...
        jcenter()
    }
    //...
}
````

**2. Enable the **jetifier** and **androidX** support in the top-level `gradle.properties` file.**

````groovy
//...
android.enableJetifier=true
android.useAndroidX=true
//....
````

**3. Update your `compileSdkVersion` in the module-level `build.gradle` file to **28+**.**

````groovy
//...
android {
    //...
    compileSdkVersion 28
    //...
}
//...
````

**4. Replace your `com.android.support.appcompat.*` dependency with the new `androidx.appcompat.*` alternative.**

````groovy
//...
dependencies {
    //...
    implementation "androidx.appcompat:appcompat:1.0.1"
    //...
}
//...
````

**5. Add the [ExoPlayer](https://github.com/google/ExoPlayer) dependency to the module-level `build.gradle` file.**

````groovy
//...
dependencies {
    //...
    implementation "com.google.android.exoplayer:exoplayer:2.9.2"
    //...
}
//...
````

### ARVI Dependencies

ARVI is comprised of several library modules, namely:

* `arvi` - core functionality (Required)
* `arvi-adapster` - [Adapster](https://github.com/arthur3486/adapster) adaptation (Optional)
* `arvi-ktx` - common extensions (Optional)
* `arvi-utils` - common utils and helpers (Optional)

The basic implementation would have to include the core module 
> ***Latest version:*** [ ![Download](https://img.shields.io/maven-central/v/com.arthurivanets.arvi/arvi.svg?label=Download) ](https://mvnrepository.com/search?q=com.arthurivanets.arvi)

`implementation "com.arthurivanets.arvi:arvi:X.Y.Z"`

Which should be added to your module-level `build.gradle` file. 

````groovy
ext {
    //...
    arviLibraryVersion = "1.0.0"
}

dependencies {
    //...
    implementation "com.arthurivanets.arvi:arvi:$arviLibraryVersion"
}
````

After that you can proceed with further implementation.
> ***See: [Basic Implementation](#basic-implementation) and [Adapster-based Implementation](#adapster-based-implementation)***

## Basic Implementation

Basic implementation consists of 3 straightforward steps, which include the proper handling of the system memory claims, creation of the playable Item View Holder, and the incorporation of the Playable Items Container.

The steps you need to take:

**1. Ensure the proper release of the active players when the application goes into background (System Memory Claims)**


<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
	
****Basic****
    
````kotlin
//...
import com.arthurivanets.arvi.PlayerProviderImpl

class ArviApplication : Application() {

    //...

    override fun onTrimMemory(level : Int) {
        super.onTrimMemory(level)

        if(level >= TRIM_MEMORY_BACKGROUND) {
            PlayerProviderImpl.getInstance(this).release()
        }
    }

    //...

}
````

****With**** `arvi-ktx`

````kotlin
//...
import com.arthurivanets.arvi.ktx.playerProvider

class ArviApplication : Application() {

    //...

    override fun onTrimMemory(level : Int) {
        super.onTrimMemory(level)

        if(level >= TRIM_MEMORY_BACKGROUND) {
            playerProvider.release()
        }
    }

    //...

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
//...
import com.arthurivanets.arvi.PlayerProviderImpl;

public final class YourApplication extends Application {

    //...

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if(level >= TRIM_MEMORY_BACKGROUND) {
            PlayerProviderImpl.getInstance(this).release();
        }
    }
    
    //...

}
````

</p></details><br>

**2. Implement your Item's `ViewHolder` based on the [`PlayableItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemViewHolder.java)**

****IMPORTANT****: Your `ViewHolder`'s `layout.xml` file must contain a [`PlayerView`](https://google.github.io/ExoPlayer/doc/reference/com/google/android/exoplayer2/ui/PlayerView.html) child with an id `@id/player_view`. 
> ***See: [BasicVideoItemViewHolder](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/basic/BasicVideoItemViewHolder.kt) and [item_video.xml](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/basic/BasicVideoItemViewHolder.kt)***

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
class BasicVideoItemViewHolder(
    parent : ViewGroup,
    itemView : View
) : PlayableItemViewHolder(parent, itemView) {

    //...

    override fun getUrl() : String {
        return "video_url..."
    }
    
    //...

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
public final class BasicVideoItemViewHolder extends PlayableItemViewHolder {

    //...

    @Override
    public final String getUrl() {
        return "video_url...";
    }

    //...

}
````

</p></details><br>

**3. Replace the regular [`RecyclerView`](https://developer.android.com/reference/android/support/v7/widget/RecyclerView) with the [`PlayableItemsRecyclerView`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemsRecyclerView.java)**

****IMPORTANT****: [`PlayableItemsRecyclerView`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemsRecyclerView.java) should be bound to the lifecycle of the Activity/Fragment (Activity/Fragment lifecycle events should be propagated to the [`PlayableItemsRecyclerView`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemsRecyclerView.java)) in order to ensure the correct handling of the item video playback.
> ***See: [`PlayableItemsRecyclerView`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemsRecyclerView.java), [`BasicVideoItemsRecyclerViewAdapter`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/basic/BasicVideoItemsRecyclerViewAdapter.kt), [`BasicVideosFragment`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/ui/basic/BasicVideosFragment.kt) and [`fragment_videos.xml`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/res/layout/fragment_videos.xml)***

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
class BasicVideosFragment : BaseFragment() {

    //...

    override fun init(savedInstanceState : Bundle?) {
        with(recyclerView) {
	    // PlayableItemRecyclerView configuration
            setPlaybackTriggeringStates(
                PlayableItemsContainer.PlaybackTriggeringState.IDLING,
                PlayableItemsContainer.PlaybackTriggeringState.DRAGGING
            )

            autoplayMode = PlayableItemsContainer.AutoplayMode.ONE_AT_A_TIME
            adapter = BasicVideoItemsRecyclerViewAdapter(
                context = context!!,
                items = VideoProvider.getVideos(count = 100, mute = true).toMutableList(),
                arviConfig = Config.Builder()
                    .cache(ExoPlayerUtils.getCache(context!!))
                    .build()
            )
        }
    }
    
    //...

    override fun onResume() {
        super.onResume()

        recyclerView.onResume()
    }

    override fun onPause() {
        super.onPause()

        recyclerView.onPause()
    }

    override fun onDestroy() {
    	recyclerView?.onDestroy()
	
        super.onDestroy()
    }
    
    //...

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
public final class BasicVideosFragment extends BaseFragment {

    //...

    @Override
    public void init(Bundle savedInstanceState) {
        mRecyclerView.setPlaybackTriggeringStates(
            PlayableItemsContainer.PlaybackTriggeringState.IDLING,
            PlayableItemsContainer.PlaybackTriggeringState.DRAGGING
        );
        mRecyclerView.setAutoplayMode(PlayableItemsContainer.AutoplayMode.ONE_AT_A_TIME);
        mRecyclerView.setAdapter(new BasicVideoItemsRecyclerViewAdapter(
            context,
            VideoProvider.getVideos(100, true),
            new Config.Builder()
                .cache(ExoPlayerUtils.getCache(context))
                .build()
        ));
    }
    
    //...

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mRecyclerView.onPause();
    }

    @Override
    public void onDestroy() {
        mRecyclerView.onDestroy();
    
        super.onDestroy();
    }
    
    //...

}
````

</p></details><br>

For more advanced use cases
> ***See: [Advanced Use Cases](#advanced-use-cases)***

## Adapster-based Implementation

[Adapster](https://github.com/arthur3486/adapster)-based implementation requires both the official [Adapster](https://github.com/arthur3486/adapster) and `arvi-adapster` module dependencies.

> ***Latest ARVI version:*** [ ![Download](https://img.shields.io/maven-central/v/com.arthurivanets.arvi/arvi.svg?label=Download) ](https://mvnrepository.com/search?q=com.arthurivanets.arvi)

`implementation "com.arthurivanets.arvi:arvi-adapster:X.Y.Z"`

While the implementation itself shares most of the steps with the [Basic Implementation](#basic-implementation), one of the things that should be taken into account is the fact that the implementation of your Item `ViewHolder` should be based on the [`AdapsterPlayableItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/arvi-adapster/src/main/java/com/arthurivanets/arvi/adapster/AdapsterPlayableItemViewHolder.java) instead of the [`PlayableItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemViewHolder.java).

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
//...
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder

class VideoItemViewHolder(
    parent : ViewGroup,
    itemView : View,
    private val resources : VideoItemResources?
) : AdapsterPlayableItemViewHolder<Video>(parent, itemView) {

    //...

    override fun getUrl() : String {
        return "video_url..."
    }
    
    //...

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
//...
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder;

public final class VideoItemViewHolder extends AdapsterPlayableItemViewHolder<Video> {

    //...
    
    @Override
    public final String getUrl() {
        return "video_url...";
    }
    
    //...

}
````

</p></details><br>

> ***See: [`VideoItemsRecyclerViewAdapter`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItemsRecyclerViewAdapter.kt), [`AdapsterVideosFragment`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/ui/adapster/AdapsterVideosFragment.kt), [`AdapsterPlayableItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/arvi-adapster/src/main/java/com/arthurivanets/arvi/adapster/AdapsterPlayableItemViewHolder.java) and [`VideoItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItemViewHolder.kt)***

For more advanced use cases
> ***See: [Advanced Use Cases](#advanced-use-cases)***

## Advanced Use Cases

Sometimes you require something more than a basic implementation, whether it's an ability to enable the caching of your videos or a way to authorize your HTTP Video requests, you name it; for that reason a list of the most common advanced use cases has been compiled.

Most common advanced use cases include, but not limited to:

**1. Video Caching**

In order to enable the video caching you should provide an instance of the [`ExoPlayer Cache`](https://google.github.io/ExoPlayer/doc/reference/com/google/android/exoplayer2/upstream/cache/Cache.html) via the ARVI [`Config`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/Config.java) to your Item ViewHolders, and then use the provided [`Config`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/Config.java) within each corresponding Item `ViewHolder`.

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
//...
import com.arthurivanets.arvi.Config

class BasicVideoItemViewHolder(
    parent : ViewGroup,
    itemView : View,
    val arviConfig : Config
) : PlayableItemViewHolder(parent, itemView) {

    //...

    override fun getUrl() : String {
        return "video_url..."
    }
    
    override fun getConfig() : Config {
        return arviConfig
    }
    
    //...

}
````

****Adapster-based****
````kotlin
//...
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder

class VideoItemViewHolder(
    parent : ViewGroup,
    itemView : View,
    private val resources : VideoItemResources?
) : AdapsterPlayableItemViewHolder<Video>(parent, itemView) {

    //...

    override fun getUrl() : String {
        return "video_url..."
    }
    
    override fun getConfig() : Config {
        return (resources?.arviConfig ?: super.getConfig())
    }
    
    //...

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
//...
import com.arthurivanets.arvi.Config;

public final class BasicVideoItemViewHolder extends PlayableItemViewHolder {

    //...

    @Override
    public final String getUrl() {
        return "video_url...";
    }
    
    @Override
    public final Config getConfig() {
        return arviConfig;
    }
    
    //...

}
````

****Adapster-based****
````java
//...
import com.arthurivanets.arvi.adapster.AdapsterPlayableItemViewHolder;

public final class VideoItemViewHolder extends AdapsterPlayableItemViewHolder<Video> {

    //...

    @Override
    public final String getUrl() {
        return "video_url...";
    }
    
    @Override
    public final Config getConfig() {
        return resources.arviConfig;
    }
    
    //...

}
````

</p></details><br>

The general [`ExoPlayer Cache`](https://google.github.io/ExoPlayer/doc/reference/com/google/android/exoplayer2/upstream/cache/Cache.html) instance can be easily created using the utility extension methods found in the [`ArviExtensions`](https://github.com/arthur3486/ARVI/blob/master/arvi-ktx/src/main/java/com/arthurivanets/arvi/ktx/ArviExtensions.kt) of the `arvi-ktx` module, or you can resort to your own [`ExoPlayer Cache`](https://google.github.io/ExoPlayer/doc/reference/com/google/android/exoplayer2/upstream/cache/Cache.html) instance creation approach; choose the approach that fits your requirements the best.

For more details
> ***See: [`BasicVideosFragment`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/ui/basic/BasicVideosFragment.kt), [`BasicVideoItemsRecyclerViewAdapter`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/basic/BasicVideoItemsRecyclerViewAdapter.kt), [`BasicVideoItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/basic/BasicVideoItemViewHolder.kt), [`AdapsterVideosFragment`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/ui/adapster/AdapsterVideosFragment.kt), [`VideoItemsRecyclerViewAdapter`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItemsRecyclerViewAdapter.kt), [`VideoItem`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItem.kt), [`VideoItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItemViewHolder.kt), [`VideoItemResources`](https://github.com/arthur3486/ARVI/blob/master/sample/src/main/java/com/arthurivanets/sample/adapters/adapster/VideoItemResources.kt), [`Config`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/Config.java), [`ArviExtensions`](https://github.com/arthur3486/ARVI/blob/master/arvi-ktx/src/main/java/com/arthurivanets/arvi/ktx/ArviExtensions.kt), [`Cache`](https://github.com/arthur3486/ARVI/blob/master/arvi-ktx/src/main/java/com/arthurivanets/arvi/ktx/ArviExtensions.kt)***

**2. HTTP Video Request Authorization**

In cases when your video requests require authorization, you can use the [`RequestAuthorizer`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/datasource/RequestAuthorizer.java) to provide the necessary auth token whenever the player requests it. The created [`RequestAuthorizer`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/datasource/RequestAuthorizer.java) should be associated with the [`ArviHttpDataSourceFactory`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/datasource/ArviHttpDataSourceFactory.java) and passed around in the ARVI [`Config`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/Config.java) object.

****RequestAuthorizer****

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
//...
import com.arthurivanets.arvi.player.datasource.RequestAuthorizer

class ArviRequestAuthorizer(private val authTokenProvider : AuthTokenProvider) : RequestAuthorizer {

    override fun getAuthorization() : String {
        return "Bearer ${authTokenProvider.getAuthToken()}"
    }

}
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
//...
import com.arthurivanets.arvi.player.datasource.RequestAuthorizer;

public final class ArviRequestAuthorizer extends RequestAuthorizer {

    //...

    @Override
    public final String getAuthorization() {
        return ("Bearer " + authTokenProvider.getAuthToken());
    }

}
````

</p></details><br>

****ARVI Config****

<details><summary><b>Kotlin (click to expand)</b></summary>
<p>
    
````kotlin
//...
val config = Config.Builder()
    .dataSourceFactory(
        ArviHttpDataSourceFactory(context.playerProvider.libraryName).apply {
            setConnectTimeout(REQUEST_CONNECT_TIMEOUT_IN_MILLIS)
            setReadTimeout(REQUEST_READ_TIMEOUT_IN_MILLIS)
            
            // Your request authorizer
            setRequestAuthorizer(ArviRequestAuthorizer(...))
        }
    )
    .build()
````

</p></details><br>

<details><summary><b>Java (click to expand)</b></summary>
<p>
    
````java
//...
final ArviHttpDataSourceFactory dataSourceFactory = new ArviHttpDataSourceFactory(PlayerProviderImpl.getInstance(context).getLibraryName());
dataSourceFactory.setConnectTimeout(REQUEST_CONNECT_TIMEOUT_IN_MILLIS);
dataSourceFactory.setReadTimeout(REQUEST_READ_TIMEOUT_IN_MILLIS);

// Your request authorizer
dataSourceFactory.setRequestAuthorizer(new ArviRequestAuthorizer(...));

// the final Config
final Config config = new Config.Builder()
    .dataSourceFactory(dataSourceFactory)
    .build();
````

</p></details><br>

> ***See: [`RequestAuthorizer`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/datasource/RequestAuthorizer.java), [`ArviHttpDataSourceFactory`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/datasource/ArviHttpDataSourceFactory.java), [`Config`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/Config.java)***

**3. ViewHolder Playback Control**

All [`Playable`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/Playable.java) Item `ViewHolder`s are capable of controlling almost every aspect of the corresponding playback, thus giving you more power in terms of the actual implementation.

For more details on what possibilities the [`Playable`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/Playable.java) gives you
> ***See: [`Playable`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/Playable.java)***

**4. ARVI Players**

All the [`Player`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/Player.java)s created using the [`PlayerProviderImpl`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/PlayerProviderImpl.java) can be used as stand alone players, as they are totally independent entities, the only thing to remember here is that you should properly handle the player binding/unbinding events to avoid the potential memory leaks and other related issues.

> ***See: [`Player`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/player/Player.java), [`PlayerProvider`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/PlayerProvider.java), [`PlayerProviderImpl`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/PlayerProviderImpl.java)***

## Contribution

See the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## Hall of Fame

<table>
    <tbody>
        <tr>
            <td valign="middle;">
                <a href="https://play.google.com/store/apps/details?id=com.arthurivanets.owly">
	                <img src="https://lh3.googleusercontent.com/FHaz_qNghV02MpQBEnR4K3yVGsbS_0qcUsEHidzfujI3V01zyLp6yo7oK0-ymILdRk9k=s360-rw" width="70" height="70"/>
                </a>
            </td>
            <td valign="middle;"><b>Owly</b></td>
    	  </tr>
    </tbody>
</table>


> Using ARVI in your app and want it to get listed here? Email me at arthur.ivanets.work@gmail.com!

## License

ARVI is licensed under the [Apache 2.0 License](LICENSE).
