function [ result ] = computeAccurace(p_labels,label_all, cost)
%COMPUTEACCURACE Summary of this function goes here
%   Detailed explanation goes here
    disp(size(p_labels,1));
    result = (size(p_labels,1) - sum(abs(p_labels - label_all)))/size(p_labels,1);
end

