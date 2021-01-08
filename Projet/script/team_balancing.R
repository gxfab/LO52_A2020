if(!(require(dplyr))){install.packages('dplyr')}
if(!(require(tidyverse))){install.packages('tidyverse')}
if(!(require(ggplot2))){install.packages('ggplot2')}



library(dplyr, warn.conflicts = FALSE)
library(tidyverse)
library(ggplot2)
library(gridExtra)

theme_set(theme_bw())

csv_file = file.choose()
data_stat = read.csv(csv_file, header = TRUE, sep = ",")

csv_file = file.choose()
data = read.csv(csv_file, header = TRUE, sep = ",")

pdf(paste(dirname(csv_file), "team_balancing_results.pdf", sep = "/"))

grid.table(summary(data_stat))

# Plot stat data
ggplot(data_stat, aes(x=n, y=score)) + 
  geom_line() +
  #  geom_smooth(method="loess", se=F) +
  labs(y="Score gap", 
       x="n", 
       title="Team balancing score for random participants (generated n times)")



# Plot the execution time
df = data %>%
  group_by(max_iter) %>%
  summarise_at(vars(t), funs(min(.)))

ggplot(df, aes(x=max_iter, y=t)) + 
  geom_point() +
  geom_smooth(method="loess", se=F) +
  labs(y="Min time (s)", 
       x="Nb iteration",
       title="Execution time of team balancing in function of iter number")

df = data %>%
  group_by(tabu_size) %>%
  summarise_at(vars(t), funs(min(.)))

ggplot(df, aes(x=tabu_size, y=t)) + 
  geom_point() +
  geom_smooth(method="loess", se=F) +
  labs(y="Min time (s)", 
       x="Tabu list size",
       title="Execution time of team balancing in function of tabu list size")


df = data[data$tabu_size == 50,]

ggplot(df, aes(x=max_iter, y=t)) + 
  geom_point() +
  geom_smooth(method="loess", se=F) +
  labs(y="Time (s)", 
       x="Nb iteration",
       title="Execution time of team balancing in function of iter number",
       subtitle="tabu_size=50")

df = data[data$max_iter == 50,]

ggplot(df, aes(x=tabu_size, y=t)) + 
  geom_point() +
  geom_smooth(method="loess", se=F) +
  labs(y="Time (s)", 
       x="Tabu list size",
       title="Execution time of team balancing in function of tabu list size",
       subtitle="max_iter=50")


# Plot score variation
df = data %>%
  group_by(max_iter) %>%
  summarise_at(vars(score), funs(min(.)))

ggplot(df, aes(x=max_iter, y=score)) + 
  geom_line() +
  #  geom_smooth(method="loess", se=F) +
  labs(y="Min score", 
       x="Iteration number", 
       title="Team balancing score in function of the iteration number")

df = data %>%
  group_by(tabu_size) %>%
  summarise_at(vars(score), funs(min(.)))

ggplot(df, aes(x=tabu_size, y=score)) + 
  geom_line() +
  #  geom_smooth(method="loess", se=F) +
  labs(y="Min score", 
       x="Tabu list size", 
       title="Team balancing score in function of the tabu size")

df = data[data$max_iter == 25,]

ggplot(df, aes(x=tabu_size, y=score)) + 
  geom_line() +
  #  geom_smooth(method="loess", se=F) +
  labs(y="Score", 
       x="Tabu list size", 
       title="Team balancing score in function of the tabu size",
       subtitle="max_iter=25")

dev.off()

