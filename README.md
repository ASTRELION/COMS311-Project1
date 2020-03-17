# Interval Treap
### COM S 311 // Programming Project 1
**Due:** 2020.03.29 23:59  
**SDK Version:** 13.0.1  
**JUnit Version:** 4  
**JavaDoc:** https://dev.astrelion.com/COMS311-Project1/package-summary.html
## Complete JavaDoc can be found [HERE](https://dev.astrelion.com/COMS311-Project1/package-summary.html)
## Required Implementations
*The following are all items that **are implemented** that were __required__ for this project*
### Interval
- `Interval(int, int)`
- `int getLow()`
- `int getHigh()`
### Node
- `Node(Interval)`
- `Node getParent()`
- `Node getLeft()`
- `Node getRight()`
- `Interval getInterv`
- `int getIMax()`
- `int getPriority()`
### IntervalTreap
- `IntervalTreap()`
- `Node getRoot()`
- `int getSize()`
- `int getHeight()`
- `void intervalInsert(Node)`
- `void intervalDelete(Node)`
- `Node intervalSearch(Interval)`
## Optional Implementations
*The following are all items that **are implemented** that were __optional__ for this project*
- `Node intervalSearchExactly(Interval)`
- `List<Interval> overlappingIntervals(Interval)`
## Auxiliary  Implementations
*The following are all items that **are implemented** that were added by myself to provide
assistance for other functions, extra functionality, or just things I thought would be fun 
to add*  
- see the full JavaDoc [here](https://dev.astrelion.com/COMS311-Project1/package-summary.html)