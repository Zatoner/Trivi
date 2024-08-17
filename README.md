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

For the time being Trivi only has one additional feature worth mentioning: Favourites, which lets you favourite apps and pin them on the home screen.

# How does it work?

Trivi is a pretty standard Jetpack Compose Android app paired with the [Google AI Client SDK](https://developer.android.com/ai/google-ai-client-sdk), one of
the goals for building Trivi was to learn how to make modern Android apps and this will be my first real attempt so it's definitely not perfect, but please let me
know if anything can be improved in the codebase! I'd be very grateful :)

Below I will go over the 2 main technical aspects of Trivi:

## Gemini

Description

## Android

Description

# How do I build it?

To successfully build Trivi on Android Studio you'll need to add 2 API keys to the project. The 2 keys required are a [OpenWeather](https://openweather.co.uk) key
and [Google Cloud](https://cloud.google.com/?hl=en) key with the [Generative Language API](https://console.cloud.google.com/apis/library/generativelanguage.googleapis.com) enabled.

# What's to come?

Description

# Gemini API Developer Competition

Description
