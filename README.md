# TagLayout-Android-Library
<img src="https://cloud.githubusercontent.com/assets/16479249/17837251/37556e4e-67cb-11e6-97cf-2cad6e270b5a.png" width="200" height="370" />

<img src="https://cloud.githubusercontent.com/assets/16479249/17837270/25f4f2ae-67cc-11e6-8c85-e21ec10a92c8.png" width="200" height="370" />

Using Taglayouts in your android project 
Using Gradle:

Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
add the dependency :

```gradle
dependencies {
   compile 'com.github.bhushanraut1811:taglayout-android-library:v1.0'
}
```

Using Maven:
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
</repositories>
```	
add the dependency
```
<dependency>
	    <groupId>com.github.bhushanraut1811</groupId>
	    <artifactId>taglayout-android-library</artifactId>
	    <version>v1.0</version>
</dependency>	
```	

#Sample Usage in Layout file:
```	
<apps.bhushanraut1811.TagsLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Ethics" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Health" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Social" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Programming" />
    

</apps.bhushanraut1811.TagsLayout>
```	
