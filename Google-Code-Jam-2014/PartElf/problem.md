# Problem

Vida says she's part Elf: that at least one of her ancestors was an Elf. But she doesn't know if it was a parent (1 generation ago), a grandparent (2 generations ago), or someone from even more generations ago. Help her out!

Being part Elf works the way you probably expect. People who are Elves, Humans and part-Elves are all created in the same way: two parents get together and have a baby. If one parent is A/B Elf, and the other parent is C/D Elf, then their baby will be (A/B + C/D) / 2 Elf. For example, if someone who is 0/1 Elf and someone who is 1/2 Elf have a baby, that baby will be 1/4 Elf.

Vida is certain about one thing: 40 generations ago, she had 240 different ancestors, and each one of them was 1/1 Elf or 0/1 Elf.

Vida says she's P/Q Elf. Tell her what is the minimum number of generations ago that there could have been a 1/1 Elf in her family. If it is not possible for her to be P/Q Elf, tell her that she must be wrong!

# Input

The first line of the input gives the number of test cases, T. T lines follow. Each contains a fraction of the form P/Q, where P and Q are integers.

# Output

For each test case, output one line containing "Case #x: y", where x is the test case number (starting from 1) and y is the minimum number of generations ago a 1/1 Elf in her family could have been if she is P/Q Elf. If it's impossible that Vida could be P/Q Elf, y should be the string "impossible" (without the quotes).

# Limits

1 ≤ T ≤ 100.
Small dataset

1 ≤ P < Q ≤ 1000.
P and Q have no common factors. That means P/Q is a fraction in lowest terms.
Large dataset

1 ≤ P < Q ≤ 1012.
P and Q may have common factors. P/Q is not guaranteed to be a fraction in lowest terms.

# Sample

## Input 
 
5
1/2
3/4
1/4
2/23
123/31488
 	
## Output 

Case #1: 1
Case #2: 1
Case #3: 2
Case #4: impossible
Case #5: 8

Note that the fifth sample case does not meet the limits for the Small input. Even if you don't solve it correctly, you might still have solved the Small input correctly.

Explanation of sample cases

In the first sample case, Vida could have a 1/1 Elf parent and a 0/1 Elf parent. That means she could have had a 1/1 Elf one generation ago, so the answer is 1.

In the second sample case, Vida could have had a 1/1 Elf parent and a 1/2 Elf parent. That means she could have had a 1/1 Elf one generation ago, so the answer is 1.

In the third sample case, Vida could have had a 0/1 Elf parent and a 1/2 Elf parent. The 1/2 Elf parent could have had a 1/1 Elf parent and a 0/1 Elf parent. That means she could have had a 1/1 Elf two generations ago, so the answer is 2.

In the fourth sample case, it's impossible to be exactly 2/23 Elf if your ancestors 40 generations ago were all 0/1 Elf or 1/1 Elf.

Note

Yes, Vida has a lot of ancestors. If that is the part of the problem that seems the most unrealistic to you, please re-read the part about Elves.

