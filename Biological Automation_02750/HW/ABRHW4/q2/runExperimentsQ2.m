%% This function runs the DHM and random learner in parallel assuming a streaming data model
function [DHMGeneralizationError, RandGeneralizationError, costcurve,queries] = runExperimentsQ2(noise,boundaryNoise)
% Input:  noise - a number in the range (0,1) to determine which percentage
%         of the data are noise
%         boundaryNoise - a flag that determines whether the noise (if any) is concentrated on the boundary
% Output: DHMGeneralizationError - a vector containing the DHM's generalization error as a function of the number of calls to the oracle
%         RandGeneralizationError - a vector containing the random learner's generalization error as a function of the number of calls to the oracle
%         costcurve - a vector containing the cost curve for the DHM learner
%         queries - a vector containing the instances that were labeled by the oracle

% This question involves implementing the DHM algorithm for learning a
% threshold function on the unit interval.

% Additionally, you will implement a random learner for performing the
% same task and compare the performance of both algorithms

% Read through the code carefully.  The parts that you have to implement
% are labeled with comments, such as  IMPLEMENT THIS

%% ALGORITHM PARAMETERS
numsamples = 500;

% generate the data. DATA is a 1 by numsamples vector of values in the
% interval [0,1]. TRUE_LABELS is a 1 by numsamples vector of labels (either
% 0 or 1)
[DATA, TRUE_LABELS,TRUE_INTERVAL] = generateDataQ2(numsamples,noise,boundaryNoise);

%% run the DHM algorithm

% vectors for identifying points in sets S and T
S = zeros(1,numsamples);
T = zeros(1,numsamples);

% Labels for the points in S and T
Slabels = zeros(1,numsamples);
Tlabels = zeros(1,numsamples);

% R is a bit vector indicating which samples have been queried by a random learner
R = zeros(1,numsamples);
queries = zeros(1,numsamples);
% these vectors keep track of the generalization errors for DHM and the random learner
% initial error is calculated assuming the default model predicts all 0's
DHMGeneralizationError = sum(TRUE_LABELS)/numsamples; % compute generalization error
RandGeneralizationError = sum(TRUE_LABELS)/numsamples; % compute generalization

% this is the main loop of the DHM algorithm
cost = 0;  % keeps track of the number of calls to the oracle
costcurve=zeros(1,numsamples);
for(t=1:numsamples)
    % *************** IMPLEMENT THIS   ***************** %
    % you will need to:
    %   (i) select a random, unlabeled example
    %   (ii) learn the appropriate models
    %   (iii) apply the logic of the DHM algorithm
    %   (iv) keep track of costs and errors
    %   (v) implement a random learner. Note: an easy way to do this is to
    %   select a random point to label each time the algorithm selects one.
    %   The vector R can be used to keep track of the sampled labeled by
    %   the random learner
    
    % Note that the DHM algorithm requires the calculation of Delta, the
    % generalization bound. The following code computes Delta. You should
    % use this (after computing hpluserr (the error by the h-plus-one
    % model) and hminuserr (the error by the h-minus-one-model). Of course,
    % you need to re-compute hpluserr and hminuserr each iteration.     
%     x = selectRandomUnlabeledPoint(R);
    x = t;
    SlabelsPos = Slabels;
    SlabelsNeg = Slabels;
    SlabelsPos(x) = 1;
    SlabelsNeg(x) = -1;
        
    [hPos flagPos] = learnQ2(S(1:x), T(1:x), SlabelsPos(1:x), Tlabels(1:x));
    [hNeg flagNeg] = learnQ2(S(1:x), T(1:x), SlabelsNeg(1:x), Tlabels(1:x));

    h = 1;
    if(flagPos == 0 && flagNeg == 0)
        S(x) = 1;
        Slabels(x) = TRUE_LABELS(x);
        
        if Slabels(x) == 1
            h = hPos;
        else
            h = hNeg;
        end
        
        newError = sum(abs(Slabels - TRUE_LABELS)) / numsamples;
        DHMGeneralizationError = [DHMGeneralizationError, newError];
        cost = cost + 1;
    else
        %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        % compute Delta DO NOT CHANGE THIS!
        delta = 0.01;
        shatterCoeff = 2*(t+1);
        beta = sqrt( (4/t)*log(8*(t^2+t)*shatterCoeff^2/delta) );
        Delta = (beta^2 + beta*(sqrt(hPos)+sqrt(hNeg)))*.025;
        %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
       
        [errPos] = getErr(hPos, S(1:x), T(1:x));
        [errNeg] = getErr(hNeg, S(1:x), T(1:x));
        
        if(errPos - errNeg > Delta)
        	Slabels(x) = 1;
            h = hPos;
        else
            Slabels(x) = 0;
            h = hNeg;
        end
    end
        
                  
    SPred = predictQ1(h, DATA);
    Slabels(S==0) = SPred(S==0);
    randError = sum(SPred ~= TRUE_LABELS)/numsamples;
    RandGeneralizationError = [RandGeneralizationError, randError];
    costcurve(x) = cost;
end
end

%% select a random unabeled point
function xr = selectRandomUnlabeledPoint(R)
UR = find(R==0); % get unlabeled points (random learning)
xr = UR(randi(length(UR)));
end