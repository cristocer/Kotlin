# Kotlin-Collect-Lyrics-Guess-Song-Game
A game where the player has to walk araound and collect lyrics of songs and guess the song name and artist.

Video presentation at : https://youtu.be/AyGWb-HbIAI 

Firstly, I reccoment watching the video. I went through all the main activities and explained and showed how to use them, and what each component does.
I did't have time to make this file and app as I would wanted but I hope is good. I have used objects/components from Material design which I hope I highlited in my video. Although, I know I didn't do them all.

I. 	In order to run the app you need to consider the following (problems that I have encoutered):

	1.	You need to run it on a device or computer emulator with RAM and graphics power. Otherwise you will get errors like Task Incomplete or Database willnnot Update in time.
	2.	Use a device that has at least 5 inch display and support Android 28. I tested it on my phone and an emulator of 5 inch display and Android 28 as it can be seen in the video.
  		The project can run on lower version or screen size devices but it won't look as good.
	3.	As it can be seen in the video I reccomend on the first run, when you open maps icon to press back button and then press maps icon again. From now on the maps will center nicely the user current location.
	4.	In your graddle file you may need to add the following source sets and dependencies for assets subfolders and services:
	
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'androidx.preference:preference:1.1.0'
    testImplementation 'junit:junit:4.12'
    implementation 'com.github.GwonHyeok:StickySwitch:0.0.15'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'com.android.support:design:23.1.1'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.android.gms:play-services-maps:11.8.0'
    implementation 'com.google.android.gms:play-services-location:11.8.0'
}


sourceSets {
    main {
        '/src/main/assets/'
        '/src/main/assets/classic/'
        '/src/main/assets/current/'
    }
}
sourceSets {
    main {
        resources.srcDirs =['/src/main/res/drawable/imagess','/src/main/res/drawable','/src/main/res']
    }
}



II.	I used the following services for my app:
	1.com.google.android.gms.maps.GoogleMap - TO use google maps
	2.com.google.android.gms.common.api.ResolvableApiException - To access device location errors
	3.com.google.android.gms.location.*- To access device location and FusedLocationProviderClient which I found interesting.
	4.com.google.android.material:material:1.0.0 - for material design objects

III.    My .kt classes are:
	1.For database I made 2 models (Song and MyModel<image,string>) and build a Database helper DBHelper to easily work with the database's objects.
	2.AppPreferences are my apps preferences . I used it to store important paramteres that the app needs to memorize after restart and I need to use in multiple places in the code.
	3.MainActivity makes a nice interface for the user and has 2 buttons that: start a new song and goes to my collection.
	4.SplashActivity that makes a loading screen and where the database is initialized.
	5.Achievements,Feedback and Share that I didn't have time to fully implement. 
	6.MapsActivity handles google maps activity and uses a fragment for layout. In here the user location is permanently updated and he can collect lyrics by pressing markers when he is in their radius.
	7.MyAdapter and MyAdapter2 that I am using to generate data for my recycleview in the MyCollection respectively GuessActivity.
	8.MyCollection that displays a recicleview(list) of songs attemped with their progress saved in the database.It also has a switch to switch between classic and current mode.
	9.GuessActivity that handles user guesses and opens MapsActivity to search and collect lyrics (by pressing the maps Icon).
