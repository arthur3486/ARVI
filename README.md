# ARVI - Autoplayable RecyclerView Items

> ***[ExoPlayer](https://github.com/google/ExoPlayer)-based Android library that makes the implementation of the autoplayable RecyclerView items an easy task***

**ARVI** will enable you to make your feeds more interactive and appealing to your end users without the need to spend a lot of your valuable time on the implementation.

[ ![Download](https://api.bintray.com/packages/arthurimsacc/maven/arvi/images/download.svg) ](https://bintray.com/arthurimsacc/maven/arvi/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
![](https://img.shields.io/badge/API-21%2B-green.svg?style=flat)

## Contents

* [Demo](#demo-youtube)
* [Getting Started](#getting-started)
* [Basic Implementation](#basic-implementation)
* [Adapster-based Implementation](#adapster-based-implementation)
* [Advanced Use](#advanced-use)
* [Contribution](#contribution)
* [Hall of Fame](#hall-of-fame)
* [License](#license)

## Demo (YouTube)

[![YouTube Video](https://github.com/arthur3486/ARVI/blob/master/preview_image_1.png)](https://www.youtube.com/watch?v=q4SrpeyW7p8)

## Getting Started

### Prerequisites

1. Make sure that you've added the `jcenter()` repository to your top-level `build.gradle` file.

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

2. Enable the **jetifier** and **androidX** support in the top-level `gradle.properties` file.

````groovy
//...
android.enableJetifier=true
android.useAndroidX=true
//....
````

3. Update your `compileSdkVersion` in the module-level `build.gradle` file to **28+**.

````groovy
//...
android {
    //...
    compileSdkVersion 28
    //...
}
//...
````

4. Replace your `com.android.support.appcompat.*` dependency with the new `androidx.appcompat.*` alternative.

````groovy
//...
dependencies {
    //...
    implementation "androidx.appcompat:appcompat:1.0.1"
    //...
}
//...
````

5. Add the [ExoPlayer](https://github.com/google/ExoPlayer) dependency to the module-level `build.gradle` file.

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

ARVI is comprised of several library modules which allows you to select only the ones that suit your needs the best.

The library modules include:

* `arvi` - core functionality (Required)
* `arvi-adapster` - [Adapster](https://github.com/arthur3486/adapster) adaptation (Optional)
* `arvi-ktx` - common extensions (Optional)
* `arvi-utils` - common utils and helpers (Optional)

The basic implementation would have to include the core module 
> ***Latest version:*** [ ![Download](https://api.bintray.com/packages/arthurimsacc/maven/arvi/images/download.svg) ](https://bintray.com/arthurimsacc/maven/arvi/_latestVersion)

`implementation "com.arthurivanets.arvi:arvi:X.Y.Z"`

So, add the library core module dependency to your module-level `build.gradle` file. 

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

And proceed with the further implementation.
> ***See: [Basic Implementation](#basic-implementation) and [Adapster-based Implementation](#adapster-based-implementation)***

## Basic Implementation

Basic implementations consists of a few straightforward steps, listed below:

1. Ensure the proper release of the active Players when the application goes into background


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

2. Implement your [`RecyclerView.ViewHolder`](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder) based on the [`PlayableItemViewHolder`](https://github.com/arthur3486/ARVI/blob/master/arvi/src/main/java/com/arthurivanets/arvi/widget/PlayableItemViewHolder.java)

//TODO

## Adapster-based Implementation

//TODO

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


> Using ARVI in your app and want it to get listed here? Email me at arthur.ivanets.l@gmail.com!

## License

ARVI is licensed under the [Apache 2.0 License](LICENSE).
