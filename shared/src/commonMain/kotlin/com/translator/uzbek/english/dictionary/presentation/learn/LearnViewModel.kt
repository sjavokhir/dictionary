package com.translator.uzbek.english.dictionary.presentation.learn

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.core.datetime.currentDateTime
import com.translator.uzbek.english.dictionary.data.model.common.QuoteModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LearnViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, LearnState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        val title = when (currentDateTime().hour) {
            in 5..12 -> LearnState.Title.Morning
            in 13..17 -> LearnState.Title.Afternoon
            in 18..21 -> LearnState.Title.Evening
            else -> LearnState.Title.Night
        }

        stateData.update {
            it.copy(
                headerTitle = title,
                quote = quotes.randomOrNull()
            )
        }
    }

    private val quotes: List<QuoteModel>
        get() = listOf(
            QuoteModel(
                quote = "The greatest glory in living lies not in never falling, but in rising every time we fall.",
                author = "Nelson Mandela"
            ),
            QuoteModel(
                quote = "Success is not final, failure is not fatal: It is the courage to continue that counts.",
                author = "Winston Churchill"
            ),
            QuoteModel(
                quote = "The only way to do great work is to love what you do.",
                author = "Steve Jobs"
            ),
            QuoteModel(
                quote = "In three words, I can sum up everything I've learned about life: it goes on.",
                author = "Robert Frost"
            ),
            QuoteModel(
                quote = "Believe you can, and you're halfway there.",
                author = "Theodore Roosevelt"
            ),
            QuoteModel(
                quote = "Happiness is not something ready-made. It comes from your actions.",
                author = "Dalai Lama"
            ),
            QuoteModel(
                quote = "Life is what happens when you're busy making other plans.",
                author = "John Lennon"
            ),
            QuoteModel(
                quote = "The future belongs to those who believe in the beauty of their dreams.",
                author = "Eleanor Roosevelt"
            ),
            QuoteModel(
                quote = "The only limit to our realization of tomorrow will be our doubts of today.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "You miss 100% of the shots you don't take.",
                author = "Wayne Gretzky"
            ),
            QuoteModel(
                quote = "A person who never made a mistake never tried anything new.",
                author = "Albert Einstein"
            ),
            QuoteModel(
                quote = "The best way to predict the future is to create it.",
                author = "Peter Drucker"
            ),
            QuoteModel(
                quote = "In the middle of difficulty lies opportunity.",
                author = "Albert Einstein"
            ),
            QuoteModel(
                quote = "Happiness is not the absence of problems; it's the ability to deal with them.",
                author = "Steve Maraboli"
            ),
            QuoteModel(
                quote = "The only thing we have to fear is fear itself.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "Your time is limited, don't waste it living someone else's life.",
                author = "Steve Jobs"
            ),
            QuoteModel(
                quote = "Success usually comes to those who are too busy to be looking for it.",
                author = "Henry David Thoreau"
            ),
            QuoteModel(
                quote = "The only journey is the one within.",
                author = "Rainer Maria Rilke"
            ),
            QuoteModel(
                quote = "If you want to lift yourself up, lift up someone else.",
                author = "Booker T. Washington"
            ),
            QuoteModel(
                quote = "The best preparation for tomorrow is doing your best today.",
                author = "H. Jackson Brown, Jr."
            ),
            QuoteModel(
                quote = "Don't wait. The time will never be just right.",
                author = "Napoleon Hill"
            ),
            QuoteModel(
                quote = "Success is walking from failure to failure with no loss of enthusiasm.",
                author = "Winston Churchill"
            ),
            QuoteModel(
                quote = "The only person you are destined to become is the person you decide to be.",
                author = "Ralph Waldo Emerson"
            ),
            QuoteModel(
                quote = "Every strike brings me closer to the next home run.",
                author = "Babe Ruth"
            ),
            QuoteModel(
                quote = "The only limit to our realization of tomorrow will be our doubts of today.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "Life is 10% what happens to us and 90% how we react to it.",
                author = "Charles R. Swindoll"
            ),
            QuoteModel(
                quote = "Be yourself; everyone else is already taken.",
                author = "Oscar Wilde"
            ),
            QuoteModel(
                quote = "You must be the change you wish to see in the world.",
                author = "Mahatma Gandhi"
            ),
            QuoteModel(
                quote = "Success is not in what you have, but who you are.",
                author = "Bo Bennett"
            ),
            QuoteModel(
                quote = "Don't count the days, make the days count.",
                author = "Muhammad Ali"
            ),
            QuoteModel(
                quote = "It always seems impossible until it is done.",
                author = "Nelson Mandela"
            ),
            QuoteModel(
                quote = "The only true wisdom is in knowing you know nothing.",
                author = "Socrates"
            ),
            QuoteModel(
                quote = "The journey of a thousand miles begins with one step.",
                author = "Lao Tzu"
            ),
            QuoteModel(
                quote = "The only place where success comes before work is in the dictionary.",
                author = "Vidal Sassoon"
            ),
            QuoteModel(
                quote = "What you get by achieving your goals is not as important as what you become by achieving your goals.",
                author = "Zig Ziglar"
            ),
            QuoteModel(
                quote = "The best revenge is massive success.",
                author = "Frank Sinatra"
            ),
            QuoteModel(
                quote = "Life isn't about finding yourself. It's about creating yourself.",
                author = "George Bernard Shaw"
            ),
            QuoteModel(
                quote = "Success is not the key to happiness. Happiness is the key to success.",
                author = "Albert Schweitzer"
            ),
            QuoteModel(
                quote = "The two most important days in your life are the day you are born and the day you find out why.",
                author = "Mark Twain"
            ),
            QuoteModel(
                quote = "I have not failed. I've just found 10,000 ways that won't work.",
                author = "Thomas A. Edison"
            ),
            QuoteModel(
                quote = "Challenges are what make life interesting, and overcoming them is what makes life meaningful.",
                author = "Joshua J. Marine"
            ),
            QuoteModel(
                quote = "You can't use up creativity. The more you use, the more you have.",
                author = "Maya Angelou"
            ),
            QuoteModel(
                quote = "The only person you should try to be better than is the person you were yesterday.",
                author = "Matty Mullins"
            ),
            QuoteModel(
                quote = "Life is short, and it's up to you to make it sweet.",
                author = "Sarah Louise Delany"
            ),
            QuoteModel(
                quote = "The best way to predict your future is to create it.",
                author = "Peter Drucker"
            ),
            QuoteModel(
                quote = "If you want to achieve greatness, stop asking for permission.",
                author = "Anonymous"
            ),
            QuoteModel(
                quote = "The only time you fail is when you fall down and stay down.",
                author = "Stephen Richards"
            ),
            QuoteModel(
                quote = "The only thing standing between you and your goal is the story you keep telling yourself as to why you can't achieve it.",
                author = "Jordan Belfort"
            ),
            QuoteModel(
                quote = "The difference between a stumbling block and a stepping stone is how high you raise your foot.",
                author = "Benny Lewis"
            ),
            QuoteModel(
                quote = "Opportunities don't happen. You create them.",
                author = "Chris Grosser"
            ),
            QuoteModel(
                quote = "The only limit to our realization of tomorrow will be our doubts of today.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "There is no elevator to success; you have to take the stairs.",
                author = "Zig Ziglar"
            ),
            QuoteModel(
                quote = "Don't watch the clock; do what it does. Keep going.",
                author = "Sam Levenson"
            ),
            QuoteModel(
                quote = "You can never cross the ocean unless you have the courage to lose sight of the shore.",
                author = "Christopher Columbus"
            ),
            QuoteModel(
                quote = "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                author = "Ralph Waldo Emerson"
            ),
            QuoteModel(
                quote = "Don't wait for the perfect moment; take the moment and make it perfect.",
                author = "Zoey Sayward"
            ),
            QuoteModel(
                quote = "It's not whether you get knocked down; it's whether you get up.",
                author = "Vince Lombardi"
            ),
            QuoteModel(
                quote = "The only way to achieve the impossible is to believe it is possible.",
                author = "Charles Kingsleigh"
            ),
            QuoteModel(
                quote = "Life isn't about finding yourself. It's about creating yourself.",
                author = "George Bernard Shaw"
            ),
            QuoteModel(
                quote = "Be the change that you wish to see in the world.",
                author = "Mahatma Gandhi"
            ),
            QuoteModel(
                quote = "The best time to plant a tree was 20 years ago. The second best time is now.",
                author = "Chinese Proverb"
            ),
            QuoteModel(
                quote = "The only thing standing between you and your goal is the story you keep telling yourself as to why you can't achieve it.",
                author = "Jordan Belfort"
            ),
            QuoteModel(
                quote = "A person who never made a mistake never tried anything new.",
                author = "Albert Einstein"
            ),
            QuoteModel(
                quote = "Success usually comes to those who are too busy to be looking for it.",
                author = "Henry David Thoreau"
            ),
            QuoteModel(
                quote = "The greatest glory in living lies not in never falling, but in rising every time we fall.",
                author = "Nelson Mandela"
            ),
            QuoteModel(
                quote = "Your time is limited, don't waste it living someone else's life.",
                author = "Steve Jobs"
            ),
            QuoteModel(
                quote = "The only limit to our realization of tomorrow will be our doubts of today.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "You miss 100% of the shots you don't take.",
                author = "Wayne Gretzky"
            ),
            QuoteModel(
                quote = "In the middle of difficulty lies opportunity.",
                author = "Albert Einstein"
            ),
            QuoteModel(
                quote = "Happiness is not the absence of problems; it's the ability to deal with them.",
                author = "Steve Maraboli"
            ),
            QuoteModel(
                quote = "The only thing we have to fear is fear itself.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "The best preparation for tomorrow is doing your best today.",
                author = "H. Jackson Brown, Jr."
            ),
            QuoteModel(
                quote = "Don't wait. The time will never be just right.",
                author = "Napoleon Hill"
            ),
            QuoteModel(
                quote = "Success is walking from failure to failure with no loss of enthusiasm.",
                author = "Winston Churchill"
            ),
            QuoteModel(
                quote = "The only person you are destined to become is the person you decide to be.",
                author = "Ralph Waldo Emerson"
            ),
            QuoteModel(
                quote = "Every strike brings me closer to the next home run.",
                author = "Babe Ruth"
            ),
            QuoteModel(
                quote = "The only limit to our realization of tomorrow will be our doubts of today.",
                author = "Franklin D. Roosevelt"
            ),
            QuoteModel(
                quote = "Life is 10% what happens to us and 90% how we react to it.",
                author = "Charles R. Swindoll"
            ),
            QuoteModel(
                quote = "Be yourself; everyone else is already taken.",
                author = "Oscar Wilde"
            ),
            QuoteModel(
                quote = "You must be the change you wish to see in the world.",
                author = "Mahatma Gandhi"
            ),
            QuoteModel(
                quote = "Success is not in what you have, but who you are.",
                author = "Bo Bennett"
            ),
            QuoteModel(
                quote = "Don't count the days, make the days count.",
                author = "Muhammad Ali"
            ),
            QuoteModel(
                quote = "It always seems impossible until it is done.",
                author = "Nelson Mandela"
            ),
            QuoteModel(
                quote = "The only true wisdom is in knowing you know nothing.",
                author = "Socrates"
            ),
            QuoteModel(
                quote = "The journey of a thousand miles begins with one step.",
                author = "Lao Tzu"
            ),
            QuoteModel(
                quote = "The only place where success comes before work is in the dictionary.",
                author = "Vidal Sassoon"
            ),
            QuoteModel(
                quote = "What you get by achieving your goals is not as important as what you become by achieving your goals.",
                author = "Zig Ziglar"
            ),
            QuoteModel(
                quote = "The best revenge is massive success.",
                author = "Frank Sinatra"
            ),
            QuoteModel(
                quote = "Life isn't about finding yourself. It's about creating yourself.",
                author = "George Bernard Shaw"
            ),
            QuoteModel(
                quote = "Success is not the key to happiness. Happiness is the key to success.",
                author = "Albert Schweitzer"
            ),
            QuoteModel(
                quote = "The two most important days in your life are the day you are born and the day you find out why.",
                author = "Mark Twain"
            ),
            QuoteModel(
                quote = "I have not failed. I've just found 10,000 ways that won't work.",
                author = "Thomas A. Edison"
            ),
            QuoteModel(
                quote = "Challenges are what make life interesting, and overcoming them is what makes life meaningful.",
                author = "Joshua J. Marine"
            ),
            QuoteModel(
                quote = "You can't use up creativity. The more you use, the more you have.",
                author = "Maya Angelou"
            ),
            QuoteModel(
                quote = "The only person you should try to be better than is the person you were yesterday.",
                author = "Matty Mullins"
            ),
            QuoteModel(
                quote = "Life is short, and it's up to you to make it sweet.",
                author = "Sarah Louise Delany"
            ),
            QuoteModel(
                quote = "The best way to predict your future is to create it.",
                author = "Peter Drucker"
            ),
            QuoteModel(
                quote = "If you want to achieve greatness, stop asking for permission.",
                author = "Anonymous"
            ),
            QuoteModel(
                quote = "The only time you fail is when you fall down and stay down.",
                author = "Stephen Richards"
            ),
            QuoteModel(
                quote = "The difference between a stumbling block and a stepping stone is how high you raise your foot.",
                author = "Benny Lewis"
            ),
        )
}