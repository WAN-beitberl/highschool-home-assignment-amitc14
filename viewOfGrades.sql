CREATE VIEW simawork.StudentGrades AS
SELECT simawork.highschool.id, simawork.highschool.grade_avg
FROM simawork.highschool
GROUP BY id;