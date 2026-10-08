REF:
declare free row as a set
for each column:
    repeat until you find a pivot column:
        find the "base": the gcd
        find the "free row": the row where you want to not be 0 in the end
            exclude previous "free rows"
        perform elementary row ops until all but 1 column has a 0 (implementation depends on strategy)
strategies:
- don't change determinant
- keep as integers
- do whatever

don't change determinant:
