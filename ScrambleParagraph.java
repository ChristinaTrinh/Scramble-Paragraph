import java.util.StringTokenizer;

public class ScrambleParagraph {
    public static void main(String[]args)
    {
        String paragraph = "According to a research at an English University, ";
        paragraph += "it does not matter in what order the letters in a word are, "; 
        paragraph += "the only important thing is that the first and last letter is at the right place. ";
        paragraph += "The rest can be a total mess and you can still read it without a problem. ";
        paragraph += "This is because we do not read every letter by itself, but the word as-a-whole!";
        String scrambled = scrambleParagraph(paragraph);
        System.out.println(scrambled);
    }

    // this method repeatively calls scrambleSentence 
    // returns the scrambled paragraph
    public static String scrambleParagraph(String p)
    {
        String paragraph = "";
        int beginIndex =0;
        for(int i =0; i<p.length(); i++)
        {
            if(p.charAt(i) == '.') // check when a sentence ends with a period
            {
                paragraph += scrambleSentence(p.substring(beginIndex,i+1));
                beginIndex = i+1; // the beginning of the next sentence
            }
        }
        return paragraph;
    }

    // this method repeatively calls scrambleWord
    // returns scrambled sentences
    public static String scrambleSentence(String s)
    {  
        String sentence = "" ;
        StringTokenizer st = new StringTokenizer(s); //return a token in a string, a token is a word that doesn't include space
        for(int i=0; i<s.length();i++)
        {
            if(s.charAt(i) == ' ') //since it passes a token, we have to add spaces first, before scrambling the word
            {
                sentence += s.charAt(i);
                i++;
            }
            if(st.hasMoreTokens())
            {
                String word = st.nextToken();
                int length = word.length();
                sentence += scrambleWord(word);
                i += length-1;
            }
        }
        return sentence;
    }

    // this method scrambles the word 
    // return the scrambled word
    public static String scrambleWord(String w)
    {
        String word = "";
        boolean test = false; // for the case like '((((The', so it can go through the first 'else if' statement
        boolean get = false; // to distinguish which stage are we swapping
        boolean letter = false; // make sure that first 'else if' statement only went through one time fo the first letter
                                // if we don't have this boolean, the cases like rese-arch, with the hyphen, won't work
        int n =1; //for cases like 'resea,.;rch', so we can jump from 'a' to 'r'
        
        // get the last index before any period, commas,...
        int endIndex = w.length()-1;
        while(endIndex>0 && (w.charAt(endIndex)<65 ||(w.charAt(endIndex)>90 && w.charAt(endIndex)<97) ||w.charAt(endIndex)>122))
            endIndex--;

        for(int i = 0; i<endIndex;i++)
        {
            if(w.charAt(i)<65 || (w.charAt(i)>90 && w.charAt(i)<97) || w.charAt(i)>122) // for cases with special characters rather than the alphabetical letters
            {
                word+=w.charAt(i);
                test = true;
            }
            else if((i==0 || test) && !letter) //for the first letter that can't be scrambled
            {
                word+=w.charAt(i);
                test = false;
                letter = true;
            }
            else{ // for the middle letters cases
                if(!get && i!=endIndex-1) // this is for 'research' => 'rs' and it only goes until 'r'
                {
                    // this while loop for case 'resea;.,rch' so that 'a' can jump to 'r'
                    while(w.charAt(i+n)<65 || (w.charAt(i+n)>90 && w.charAt(i+n)<97) || w.charAt(i+n)>122)
                        n++;
                    word += w.charAt(i+n);
                    get = true; // this makes sure the next letter will jump to the 'else' case
                }
                else // this is for 'research' => 'rse' and it goes until 'c'
                {
                    if(i==endIndex-1 && ((endIndex+1)%2 != 0)) //for 'research, this is when the letter is 'c' and when the word 'research' has odd length
                        word += w.charAt(i);
                    else // this is for any other cases including when it reaches the index next to the last index and the word has even length
                    {
                        word += w.charAt(i-n);
                        get=false; // this makes sure the next letter falls into 'if' case
                        n=1; // reset everything 
                    }
                }
            }
        }
        word += w.substring(endIndex,w.length()); // add the last letter plus any special characters behind it
        return word;
    }
}