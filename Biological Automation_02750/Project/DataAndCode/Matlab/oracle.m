function label = oracle(id)
% input: id - patient id
% output: label - true label
m = load('trueLabels.mat');m=m.trueLabels;
label = m(id);
end