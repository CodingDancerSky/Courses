function totalYeild = bandit

%% run a multi-armned bandit simulation for question 3
% Output: totalYeild - the total ethanol yeild.

numgenes = 50;
numreplicates = 10;
eta = 0.2;
N = numgenes * numreplicates
yeild = zeros(1, numgenes);
nums = zeros(1, numgenes);
change = ones(1, numgenes);

for i = 1: numgenes
    yeild(i) = RNAiSim(i);
    nums(i) = nums(i) + 1;
end


records = zeros(N, numgenes);
records(1,:) = yeild;
change = change * (eta / (numgenes - 1));
[x, y] = max(yeild)
change(y) = 1 - eta;
rand = mnrnd(1, change);

for(j = 2: N - numgenes)
    indx = find(rand);
    yeild(indx) = yeild(indx) + RNAiSim(indx);
    nums(indx) = nums(indx) + 1;
    records(indx, nums(indx)) = RNAiSim(indx);
    UCB = zeros(1,numgenes);
    
    for(k = 1:numgenes)
        [M, S] = normfit(records(k, 1:nums(indx)));
        UCBk = M + 1.96 * (S / sqrt(j));
        if(UCBk > UCB(k))
            UCB(k) = UCBk;
        end
    end
    
    change_k = ones(1, numgenes);
    change_k = change_k * (eta / (numgenes - 1));
    [x,y] = max(yeild);
    change_k(y) = 1 - eta;
    rand = mnrnd(1, change_k);
end

totalYeild = sum(yeild);

end