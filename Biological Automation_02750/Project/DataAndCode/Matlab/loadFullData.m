function [data, labels ] = loadFullData(t)
%LOADFULLDATA Summary of this function goes here
%   Detailed explanation goes here
    m = load('trueLabels.mat');
    labels = m.trueLabels;
    [data] = csvread('stream.csv');
    if(t==1)
        data = data(:,1:3);
    elseif(t==2)
        data = data(:,4:28);
    elseif(t==3)
        data = data(:,29:128);
    elseif(t==4)
        data = data(:,129:130);
    end
end

