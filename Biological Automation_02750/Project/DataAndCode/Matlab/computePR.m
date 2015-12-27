function [ precision, recall ] = computePR(p_labels,label_all)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    true = sum(p_labels.*label_all);
    precision = true/sum(p_labels);
    recall = true/sum(label_all);
end

