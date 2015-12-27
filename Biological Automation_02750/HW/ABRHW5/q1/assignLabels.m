function L = assignLabels(L, u, v, T, nsample)
%% Assign labels to every leaf according to the label of the subtree's root node
%   Input:
%       L: predicted label of each node
%       u: current node
%       v: subtree's root node
%       T: tree (cell object of length 3, see DH_SelectCase1.m for details)
%       nsample: number of samples, 1000
%   Output:
%       L: predicted label of each node
% Hint: you should implement this function recursively
    L(u) = L(v);
    children = find(T{3} == u);
    
    if(size(children, 2) == 0)
        return;
    end
    
    for(i = 1: size(children, 2))
        c = children(i);
        L = assignLabels(L, c, v, T, nsample);
    end
    

end