# Trivi (τριβή)

Trivi is a minimal Android launcher designed to reduce screen time and promote healthier choices by leveraging [Gemini](https://ai.google.dev/gemini-api/docs)

![trivi_banner](https://github.com/user-attachments/assets/8eecce46-a141-480b-955a-4dc75b9c6e4d)
> Android Launcher App

# What is it?

Trivi is an Android launcher app designed to replace the default home app, Trivi is minimalistic by nature to avoid distraction and reduce "friction". Trivi also
uses the [Gemini API](https://ai.google.dev/gemini-api/docs) to provide helpful summaries to the user and recommendations to help them get information faster 
and spend less time on their screen. This is still in its early stages so the feature set is quite basic, but ultimately since Trivi's goal is to stay minimal 
the feature set won't expand that much. Gemini can also be quite unreliable at times due to a rather simplistic implementation for the time being, plans are definitely to
improve its accuracy.

The following sub-sections will showcase Trivi's current core features:

## Spotlight widget

<img src=https://github.com/user-attachments/assets/fd192293-5500-4495-aeca-84846aaf2b4d height="15%">

> Spotlight Recording

This widget allows the user to have a glanceable key information on their home screen, on top of that Gemini will also suggest relevant apps depending on what's written
on the spotlight widget. The spotlight widgets also refreshes periodically. (this widget was heavily inspired by Google's "Pixel at a glance" widget)

## Follow up

<img src=https://github.com/user-attachments/assets/d3ba9529-cb67-420c-a055-2e5c0720d67e height="15%">

> Follow Up Recording

If the user wishes to expand on something like their latest conversation that they missed they can by prompting Gemini: `Can you summarise my latest messages on WhatsApp?`, 
this will generate a summary that the user can ask follow up details about. The example shown above is rather simple due to its personal nature. Note, one big drawback that 
Trivi currently has, is that it doesn't know what you're sending so it might sometimes not fully understand the full context of the messages received.

## App drawer

<img src=https://github.com/user-attachments/assets/974c08b0-c48f-416f-b580-0abb06380ccd height="15%">

> App Drawer Recording

Like most traditional Android launchers, Trivi also has a normal app drawer for opening any app the user has installed on their device.

## Other features

<!-- personallity setting? -->
For the time being Trivi only has one additional feature worth mentioning: Favourites, which lets you favourite apps and pin them on the home screen.

# How does it work?

Trivi is a pretty standard Jetpack Compose Android app paired with the [Google AI Client SDK](https://developer.android.com/ai/google-ai-client-sdk), one of
the goals for building Trivi was to learn how to make modern Android apps and this will be my first real attempt so it's definitely not perfect, but please let me
know if anything can be improved in the codebase! I'd be very grateful :)

Below I will go over the 2 main technical aspects of Trivi:

## Gemini

<!-- Gemini Context, prompt given, gemini tweaks, model name, history -->

For Gemini to accurately provide useful information it is given a wide range of user context with specific prompting to generate informative answers.
Gemini is currently aware of (if given permission):

- Time
- User Name
- User Info (short description that the user input themselves)
- User Location
- Current Weather
- 24 Hour Weather Forecast
- User Notifications in the Past 12 Hours
- Top Used Apps by the User

The full prompt and it's other configuration changes can be found in [Constants.kt](app/src/main/java/com/aboe/trivilauncher/common/Constants.kt) and
[GetGeminiPromptUseCase.kt](app/src/main/java/com/aboe/trivilauncher/domain/use_case/get_gemini_prompt/GetGeminiPromptUseCase.kt).

Trivi currently uses [Gemini 1.5 Flash](https://ai.google.dev/gemini-api/docs/models/gemini#gemini-1.5-flash) for all of it's content due to it's
speed and relative cheap cost compared to Pro since there's potentially many calls being made (10 minute cooldown for spotlight widget). Additionally,
Trivi has a history log for follow up chats for better user context.

## Android

<!-- Attemtp at MVVM + Clean, Retrofit, Room, Dagger Hilt, Notification service, App fetching, Weather -->

As mentioned earlier, one of Trivi's goal was to learn how to make modern Android apps, therefore Trivi attempts to have a MVVM layout + a Clean architecture
while using Kotlin, Jetpack Compose, Coroutines, Flows, etc... Trivi also leverages Dagger Hilt (dependency injection) to make the codebase less verbose.

All user notifications are stored locally (if given permission) using Room for ease of use while storing and querying data. For API calls to OpenWeather Trivi
uses Retrofit.

# How do I build it?

To successfully build Trivi on Android Studio you'll need to add 2 API keys to the project. The 2 keys required are a [OpenWeather](https://openweather.co.uk) key
and [Google Cloud](https://cloud.google.com/?hl=en) key with the [Generative Language API](https://console.cloud.google.com/apis/library/generativelanguage.googleapis.com) enabled.
Once the keys are acquired, add them to the `local.properties` file found in the root directory of the project. Here's what it's supposed to look like:

```
geminiKey=KEY
openWeatherKey=KEY
```

That should be everything, you should be able to build the project once this step is completed.

# What's to come?

- A proper onboarding screen
- Search feature in app drawer and other app drawer improvements
- A settings screen using Data Store instead of SharedPreferences
- Removing SharedPreferences code
- Commenting code base for better open source accessibility
- Grant Trivi access to user's calendar for better recommendations
- Try out Gemini Nano for offline capabilities and much better user privacy (all local)
- Use more advanced Gemini features like fine tuning and function calling
- Dark mode
- App opening animation
- Wallpaper support + monet theming
- Haptics
- UI Improvements
- Better project architecture
- Widget support?

# Featured on

TODO

# Gemini API Developer Competition

TODO
[old repo](https://github.com/Zatoner/TriviLauncher)
