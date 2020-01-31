# Seguridad
Trabajos de seguridad 2020-1

Vigenere: 
[Vigenere](https://pages.mtu.edu/~shene/NSF-4/Tutorial/VIG/Vig-Kasiski.html)  
[Solving vigenere](https://www.it.uu.se/edu/course/homepage/security/vt09/labs/vigenere.html)


https://en.wikipedia.org/wiki/Kasiski_examination


https://crypto.interactive-maths.com/kasiski-analysis-breaking-the-code.html
Once the length of the keyword is known, the ciphertext can be broken up into that many simple substitution cryptograms. That is, for a keyword of length 9, every 9-th letter in the ciphertext was encrypted with the same keyword letter. Given the structure of the Vigenere tableau, this is equivalent to using 9 distinct simple substitution ciphers, each of which was derived from 1 of the 26 possible Caesar shifts given in the tableau. The pure Kasiski method proceeds by analyzing these simple substitution cryptograms using frequency analysis and the other standard techniques. A variant of this method, proposed by the French cryptographer Kerckhoff, is based on discovering the keyword itself and then using it to decipher the cryptogram. In Kerckhoff's method, after the message has been separated into several columns, corresponding to the simple substitution cryptograms, one tallies the frequencies in each column and then uses frequency and logical analysis to construct the key. For example, suppose the most frequent letter in the first column is 'K'. We would hypothesize that 'K' corresponds to the English 'E'. If we consult the Vigenere tableau at this point, we can see that if English 'E' were enciphered into 'K' then row G of the table must have been the alphabet used for the first letter of the keyword. This implies that the first letter of the keyword is 'G'. 


Once you know the length of the keyword, you can write out the ciphertext is that many columns. For example, if the keyword is 6 letters long, write the ciphertext in 6 columns. Now the letters in each column have all been encoded using the same keyword letter, and thus the same shift. So a bit of frequency analysis will help us with each individual column.

