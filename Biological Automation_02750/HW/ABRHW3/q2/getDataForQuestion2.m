function [genomes, labels] = getDataForQuestion2

%% Create data for question 2
% Output: genomes - a 500 by 10000 binary matrix representing the genomes of 500 patients
%         labels - a 1 by 500 binary vector of labels

numGenomes = 500;
genomeSize = 10000;
labels = zeros(1,numGenomes);
genomes = round(rand(numGenomes,genomeSize));
p = [100, 390, 449, 821, 901];
genomes(:,p) = 0;
for(i=1:numGenomes)
   if(rand>.5)
      labels(i) = 1;
      p = p(randperm(length(p)));
      genomes(i,p(1))=1;
      if(rand>.9)
          genomes(i,p(2))=1;
      end
   end
end
end