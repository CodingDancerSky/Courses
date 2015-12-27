function [L,loss] = DH_SelectCase1(data, labels, T)
%% Main function for DH algorithm
% Input:
%       data: a 1000 by 25 matrix; each row represents the sample from the
%       ith patient. each column is a protein concentration level
%       labels: an 1000 by 1 matrix; each element (i, 1) represents the
%       true subtype of ith sample.
%       T: a cell of length 3
%           T{1}: the linkage of hierachical clustering tree on data,
%           for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
%           T{2}: a vector denoting the size of subtree rooted at each
%           node (the number of children plus 1 for itself)
%           T{3}: a vector denoting the parent for each node, the root
%           node's parent is set to 0
% Output:
%       L: a 1 by 1000 vector; ith element represents the predicted
%       label for ith sample.
%       loss: a 1 by 1000 vector; ith element represents the loss after
%       ith round querying
    root = find(T{3} == 0);
    P = [root];
    L = zeros(1,1000);
    L(root) = 1;
    n = zeros(1,1999);
    p1 = zeros(1,1999);
    nsample = 1000;
    loss = zeros(1,1000);
    
    for (i = 1: nsample)
%         disp(i);
        leaves = [];
        while(size(leaves,2) == 0)
            v = P(randsample(size(P,2), 1, true, T{2}(P)));
            leaves = getLeaves([], v, T, nsample);
            leaves = leaves(find(n(leaves) == 0));
        end
        z = leaves(randsample(size(leaves,2), 1, true));
        l = labels(z);
        [n, p1] = updateEmpirical(n, p1, v, z, l, T);
        [Pbest, Lbest] = chooseBestPruningAndLabeling(n, p1, v, T, nsample);
        P = [P(P ~= v), Pbest];
        L(Pbest) = Lbest;
        
        for(j = 1:size(P,2))
            v = P(j);
            u = v;
            L = assignLabels(L, u, v, T, nsample);
        end
        
        loss(i) = computeLoss(L(1:1000), labels);
    end

end