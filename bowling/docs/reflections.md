Our test suite usually ends up like:

 - Gutter game = all zeroes, (score = 0)
 - One pin down in each roll, (score = 20)
 - Spare in first roll, one pin down in each other roll, (score = 10 + 1 + 18 = 29)
 - Spare in last roll, one pin down in each other roll, (score = 18 + 10 + 1 = 29)
 - Strike in first roll, one pin down in each other roll, (score = 10 + 1 + 1 + 18 = 30)
 - Strike in last roll, one pin down in each other roll, (score = 18 + 10 + 1 + 1 = 30)
 - Golden game = all strikes (score = 300)

The highligts of the BowlingGameKata is:

 - separating storing the roll results from calculating the score
 - choosing a suitable data structure to store the results in
 - choose a suitable iterator to calculate the score from
