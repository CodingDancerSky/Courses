function totalYeild = replicates

%% run a replicate simulation for question 3
% Output: totalYeild - the total ethanol yeild.

numgenes = 50;
numreplicates = 10;

%% IMPLEMENT ALGORITHM
totalYeild = 0;

for(i = 1:numgenes)
    for(j = 1:numreplicates)
        totalYeild = totalYeild + RNAiSim(i);
    end
end
end