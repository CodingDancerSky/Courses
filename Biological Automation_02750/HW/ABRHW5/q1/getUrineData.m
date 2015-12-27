function [data, labels, T] = getUrineData
%% Create data for question 1, part E
% Output:
%       data: a 1000 by 25 matrix; the ith row is the data from the ith patient. Element (i,j) is the concentration of the jth protien in the ith patient.
%       labels: an 1000 by 1 matrix; each element (i, 1) represents the
%       true subtype of ith sample.
%       T: a cell of length 3
%           T{1}: the linkage of hierachical clustering tree on data,
%           for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
%           T{2}: a vector denoting the size of subtree rooted at each
%           node (the number of children plus 1 for itself)
%           T{3}: a vector denoting the parent for each node, the root
%           node's parent is set to 0
nsample_sub = 250;
mu1 = rand(1,25)*11;
sigma1 = genRandCovMatrix(25);
r1 = mvnrnd(mu1,sigma1,nsample_sub);
labels1 = zeros(250,1);

mu2 = rand(1,25)*13.5;
sigma2 = genRandCovMatrix(25);
r2 = mvnrnd(mu2,sigma2,nsample_sub);
labels2 = zeros(250,1);

mu3 = [mu1(1:12) mu2(13:25)];
sigma3 = genRandCovMatrix(25);
r3 = mvnrnd(mu3,sigma3,nsample_sub);
labels3 = ones(250,1);

mu4 = [mu2(1:12) mu1(13:25)];
sigma4 = genRandCovMatrix(25);
r4 = mvnrnd(mu4,sigma4,nsample_sub);
labels4 = ones(250,1);

data=[r1;r2;r3;r4];
labels = [labels1;labels2;labels3;labels4]; %1000 by 1

% permute the data
rp = randperm(1000);
data=data(rp,:);
labels=labels(rp);

Z = linkage(data,'ward','euclidean');
nsample = nsample_sub * 4;

parent = zeros(1, Z(end,2)+1);
parent(end) = 0;
sub_tree_sizes = zeros(1, Z(end,2)+1);
sub_tree_sizes(1:nsample) = 1;
sub_tree_sizes(end) = nsample;
for i = 1:size(Z,1)
    left = Z(i,1);
    right = Z(i,2);
    current = i + nsample;
    sub_tree_sizes(current) = sub_tree_sizes(left) + sub_tree_sizes(right);
    parent(left) = current;
    parent(right) = current;
end
T{1} = Z;
T{2} = sub_tree_sizes;
T{3} = parent;
end

function M = genRandCovMatrix(n)
% Generate a random covariance matrix
M = rand(n,n);
M = M+M';
M = M + n*eye(n);
end