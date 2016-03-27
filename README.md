# JChains
A Markov Chain library for Java. Supports extensible tokenization and an arbitrary lookback.

##Requirements

**Build:**
 - Maven 3
 - JDK 8
 
**Run:**
 - JRE 8

##Building

Run `mvn clean package` to compile the project into a runnable fat jar. This jar will be located inside the `/target` directory. Requires Maven to be installed on your path.

##Examples

These are a few examples of implementations.

####Sentence Generator

**Source**

```java		
int mapInitialSize = 1000;
int lookback = 3;
int numSent = 10;
String filepath = "test.txt"

TokenHolder<String> tokenHolder = new MapTokenHolder<>(mapInitialSize);

log.info("Starting FileTokenizer creation...\n");
FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, lookback, filePath);

log.info("Generating Token Lists...\n");
List<List<Token>> tokensLists = new LinkedList<>();
for (int i = 0; i < numSent; i++) {
	tokensLists.add(fileTokenizer.generateTokenList());
}

log.info("Printing Tokens...\n" + "===============\n");
tokensLists.forEach(l -> fileTokenizer.outputTokens(l));

```	
 


**Outputs**


######Donald Trump

>Be careful, Lyin ' Ted Cruz just used a picture of Melania, he did.
>
>All others lived Wow, President Obama- close down the flights from Ebola infected areas right now, before it is too late!
>
>Why is it that the horrendous protesters, who have watched ISIS and many other problems develop for years.
>
>Crazy @ megynkelly show, NATO!

######William Shakespeare

>ARIEL: That you had not going for gage; There are causes again, and be medicined.
>
>FERDINAND: Why, Thou likest that do wish their honour confined, I am the salt Et all the table of your city, it, 'Tis widow's ears, thou start enough, I stand with him passion, what ground, lamented for each man and story As I do I thought To my throat But now he his mother, Which gathered my sweet queen been consent.
>
>DUKE OF DOUGLAS: Or will watch you had stormed upon my three, he shall have Brutus.
>
>SUFFOLK: What can know that I must not an Alexander lives, shrills even thou wouldst Execute thy father by our Olland to correct a bird Will go hand dies on their veins befeen the several judgment, Bolder'd in Tom's hair-kills, in this dish pear, out of my doublet wonder in that, where, if you to thee And feed To jester to plant the hate To slip it is any man's reports, sir, sir; I will not speak to a pride and marry: He cannot have my father's friendly Remedy.

######Communist Manifesto

>In Poland they thus served as in the different localities in agriculture with the interests of the vocation of the workers may immediately following proletarian movement is supreme to historical movement against the country, glaring character, the violent overthrow of the immense majority of exchange, i.e. When Christian ideas without the modern bourgeoisie must first direct their existence of the workers are of rising bourgeoisie itself put on their views and that we replace home, serfs; in the holy, and more. That culture, of enlightenment of class, not at last word, as the bourgeois conditions, or rather, so much more extensive use, you reproach us.
>
>They had cut off the propagation of America, point out into the factory, of distant lands and which the ruling class that writers who have not as to save from the disastrous effects of its own class is allowed to appeal to abolish this only of floggings and universal excitement, has no longer compatible with its conditions. The whole of these systems were, in view of women.

######Godel Escher Bach

>Then, not only shadows cast in a windowpane merge; ultimately, age twenty-one, Computers by using the King's Theme, how we shall consider ME as an infinite series of the other end. Perhaps that is a flaw here- an ironclad way. On the other of these frailties, and to let their powers of association run free.

