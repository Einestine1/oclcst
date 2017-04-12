# OCL Class Specs Testbed
OCL Class Specs Testbed

Introduction:

This project was developed during my MS thesis research. The concept here is to generate optimized test sequences for OCL class specifications. As far as the functionality is concerned, this project is a proof of concept at the moment covering most of the aspects of OCL class constraint specifications and fully functional Abstract Finite State Machine (AFSM) along with applied Multi Object Genetic Algorithms (MOGA).
Features
Test Sequences from OCL class specifications.
Test Sequence Optimization using Multi Objective Genetic Algorithm
Automated Generation and Optimization of the Object Constraint Language (OCL) based test sequences

Functional phases:
1.	Abstract Finite State Machine (AFSM) creation after OCL parsing and semantics analysis.
2.	Creation of initial population of test sequences to be used by Multi Objective Genetic Algorithms (MOGA).
3.	Execution of the MOGA with the following fitness functions

	1. Calculate Fitness By Coverage 
		Initialize CF:=0, wCoveredOnce, wCoveredTwice, wCoveredMoreThanTwice
		For each Chromosome c in the current population 
			For each Gene g in c 
				If g occurs once  
					CF = CF + wCoveredOnce
				End 
				If g occurs twice 
					CF = CF + wCoveredTwice 
		End
		If g occurs more than twice 
		CF = CF + wCoveredMoreThanTwice
		End  
			End
		Set coverage fitness of c equals CF
		End
		
		
		
		wCoveredOnce, wCoveredTwice, wCoveredMoreThanTwice are, problem specific arbitrary weight-ages, for at least one state coverage, a state covered twice and a state covered more than two times.

		Description of calculation of coverage weights is
		•	If a transition is covered once chromosome is given additional positive weight-age, it rewards a chromosome for covering a transition.
		•	If a transition is not covered at all by a chromosome it is given additional negative weight-age, it reward a chromosome negatively for not covering a transition.
		•	If a transition is covered more than once by a chromosome it is given additional negative weight-age, it rewards negatively due to repetition.

		
		
	2. Calculate Fitness By Validity 
		Initialize VF:=0, wSState, wInSeq, wNotInSeq
		For each Chromosome c in the current population 
		If c starts with an initial state
		VF = VF + wSState
		End
			For each Gene g in c 
				If g is in sequence   
				VF = VF + wInSeq  
				Else
				VF = VF + wNotInSeq 
			End
		Set validity fitness of c equals VF
		End
		
		
		
		wSState, wInSeq and wNotInSeq are, problem specific arbitrary weight-ages, for starting with initial state, being in sequence and not being in sequence respectively.

		Description of the weight calculation for validity fitness is 
		•	Initial state weight, if the first gene of the chromosome has an initial state of AFSM as from state then this weight is added else skipped.
		•	Sequence weight for call sequence, we calculate the quality of chromosome by the sequence of method calls and reward each chromosome by following formula
			o	If any of the method calls (genes) is in a valid sequence then a positive weight is added to the second fitness value.
			o	If any of the method calls (genes) is not in a valid sequence then a negative weight is added


