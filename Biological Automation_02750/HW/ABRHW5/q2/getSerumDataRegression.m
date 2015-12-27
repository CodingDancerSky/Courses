function [data, labels] = getSerumDataRegression
%% Create data for question 2
% Output:
%       data: a 1000 by 25 matrix; the ith row is the data from the ith patient. Element (i,j) is the concentration of the jth protein in the ith patient.
%       labels: an 1000 by 1 matrix; each element (i, 1) represents the survival time for the ith patient
%rng('default'); % always use the same seed
data=rand(1000,25);
rad=0.55;
for(i=1:size(data,1))
    if(sum((data(i,1:2)-[0 0]).^2)<rad^2 )
        labels(i) = max(36,randn+60);
    elseif(rand<.25 )
        data(i,1) = rand*.5;
        data(i,2) = rand*.5;
        labels(i) = max(36,randn+60);
    else
        labels(i) = max(1,randn+12);
    end
end
end
