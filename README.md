# SWEN90004Assignment2
##Java Wealth Distribution Model

##Authors
- Xuelin Zhao 801736 xuelinz@student.unimelb.edu.au
- Feng Zhao 838219 fengz1@student.unimelb.edu.au

##Usage
Use command line
- javac *.java
- java Simulator 100 5 5 10 75 0.25 10 5 1000

##Parameters			   															 		   
- [numOfPeople]     100
- [maxVision]       5
- [maxMetabolism]   5
- [minLifeSpan]     10
- [maxLifeSpan]     75
- [percentBestLand] 0.25
- [growInterval]    10
- [numOfGrow]       5
- [ticknum]         1000

##Output
After running the Simulator, output.csv and lorenz.csv would be generated

- output.csv contains Low(percent) Mid(percent) High(percent) LowAverage MidAverage HighAverage MaxWealth MinWealth Gini

- lorenz.csv contains 100 points each line range from 0 to 100 
