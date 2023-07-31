package com.translator.uzbek.english.dictionary.android.design.localization

import com.translator.uzbek.english.dictionary.core.helpers.Constants
import com.translator.uzbek.english.dictionary.shared.appUrl
import com.translator.uzbek.english.dictionary.shared.appVersion
import com.translator.uzbek.english.dictionary.shared.deviceVersion

fun stringResourcesEnglish() = StringResources(
    appName = "Uzbek-English dictionary",
    learn = "Learn",
    dictionary = "Dictionary",
    statistics = "Statistics",
    settings = "Settings",
    learning = "Learning",
    dailyGoal = "Daily goal",
    showTranscription = "Show transcription",
    uzbek = "Uzbek",
    english = "English",
    preferences = "Preferences",
    appLanguage = "App language",
    theme = "Theme",
    light = "Light",
    dark = "Dark",
    system = "System",
    reminder = "Reminder",
    everyday = "Everyday",
    soundEffects = "Sound effects",
    autoPronounce = "Automatically pronounce English words",
    countNewWords = {
        "$it new words"
    },
    progress = "Progress",
    createBackup = "Create backup",
    restoreData = "Restore data",
    resetProgress = "Reset progress",
    general = "General",
    otherApps = "Other languages and apps",
    feedback = "Feedback",
    feedbackRegarding = {
        "Feedback regarding $it [$appVersion - $deviceVersion]"
    },
    share = "Share",
    shareDescription = """
        Introducing the Uzbek-English Dictionary app, the perfect tool to enhance your English language skills while exploring the richness of the Uzbek language. This comprehensive app offers a wide range of features to facilitate learning, allowing you to master vocabulary, improve pronunciation, and track your progress effortlessly.
        
        $appUrl
    """.trimIndent(),
    rateUs = "Rate us",
    appVersion = "App version",
    telegramBot = "Telegram Bot",
    sendUs = "Send us an email",
    feedbackDescription = "Please write your email in English.\n\nIf possible, please include photos of the bug or screenshots.",
    helpUsImprove = "Help us improve!",
    dailyGoalDescription = "How many new words do you want to learn per day?",
    youWillLearn = {
        "You will learn $it new words monthly"
    },
    save = "Save",
    notSet = "Not set",
    repeat = "Repeat",
    selectAll = "Select all",
    mondayShort = "Mon",
    tuesdayShort = "Tue",
    wednesdayShort = "Wed",
    thursdayShort = "Thu",
    fridayShort = "Fri",
    saturdayShort = "Sat",
    sundayShort = "Sun",
    newWords = "New words",
    todayWords = { today, all ->
        "Today: $today/$all"
    },
    currentStreak = "Current streak",
    countDays = {
        "$it days"
    },
    week = "Week",
    month = "Month",
    quarter = "Quarter",
    year = "Year",
    completeLearned = "Complete learned",
    bestStreak = "Best streak",
    startOfLearning = "Start of learning",
    countWords = {
        "$it words"
    },
    countLearned = {
        "Learned: $it"
    },
    countNew = {
        "New: $it"
    },
    countLearning = {
        "Learning: $it"
    },
    countSkipped = {
        "Skipped: $it"
    },
    addDictionary = "Add dictionary",
    searchForWords = "Search for words",
    enterAtLeastOneLetter = "Enter at least 1 letter",
    dictionaryTitle = "Dictionary title",
    add = "Add",
    newDictionary = "New dictionary",
    editDictionary = "Edit dictionary",
    enterDictionaryTitle = "Enter dictionary title",
    addWord = "Add word",
    editWord = "Edit word",
    word = "Word",
    translation = "Translation",
    transcription = "Transcription",
    enterWord = "Enter word",
    enterTranslation = "Enter translation",
    enterTranscription = "Enter transcription",
    editThisDictionary = "Edit this dictionary",
    removeThisDictionary = "Remove this dictionary",
    clearThisDictionary = "Clear this dictionary",
    cancel = "Cancel",
    ok = "OK",
    confirmation = "Confirmation",
    confirmationResetProgress = "Are you sure you want to reset progress for each word in this dictionary?",
    confirmationResetAllProgress = "Are you sure you want to reset all progress?",
    confirmationRemoveDictionary = "Are you sure you want to remove dictionary with all of its words?",
    confirmationRemoveWord = "Are you sure you want to remove this word?",
    confirmationClearDictionary = "Are you sure you want to remove all the words from this dictionary?",
    remove = "Remove",
    edit = "Edit",
    copyToDictionary = "Copy to dictionary",
    markAsAlreadyKnown = "Mark as already known",
    resetProgressForThisWord = "Reset progress for this word",
    wordActions = "Word actions",
    dictionaryActions = "Dictionary actions",
    learned = "Learned",
    new = "New",
    learningRepeats = {
        "Learning ($it/${Constants.defaultRepeats} repeats)"
    },
    skipped = "Skipped",
    goodMorning = "Good morning \uD83D\uDC4B",
    goodAfternoon = "Good afternoon \uD83D\uDC4B",
    goodEvening = "Good evening \uD83D\uDC4B",
    goodNight = "Good night \uD83D\uDC4B",
    goPremium = "Go Premium",
    premiumFeatures = """
        • Full access to all lessons
        • Unlock review tests
        • No Ads!
    """.trimIndent(),
    dictionariesChosen = {
        "$it dictionaries chosen"
    },
    learnNewWords = "Learn new words",
    memorizedToday = { today, dailyGoal ->
        "Memorized today: $today of $dailyGoal"
    },
    repeatWords = "Repeat words",
    youHaventRepeatedWords = "You haven't repeated any words yet",
    wordsForRepeat = {
        "Words for repeat will show up in $it"
    },
    chooseCategoriesToRepeat = "Choose categories to review words from",
    wordsToRepeat = {
        "Words to repeat: $it"
    }
)