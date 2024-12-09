
ALTER TABLE "sessionAnswers" DROP COLUMN answer_id;
ALTER TABLE "sessionAnswers" RENAME COLUMN user_answer TO answer;
ALTER TABLE "sessionAnswers" ADD COLUMN correct BOOLEAN;