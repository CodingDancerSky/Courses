function leaves = getLeaves(leaves, v, T, nsample)
%% Get all leaf nodes in the subtree Tv rooted at v
% Input:
%       leaves: previously found leaves
%       v: current root node
%       T: tree (cell object of length 3, see DH_SelectCase1.m for details)
%       nsamples: number of samples, 1000
% Output:
%       leaves: leavs in the subtree Tv rooted at v
% Hint: you should implement this function recursively
    children = find(T{3} == v);
    
    if(size(children, 2) == 0)
        leaves = [leaves, v];
    else for(i = 1: size(children, 2))
        leaves = getLeaves(leaves, children(i), T, nsample);
    end
      
end