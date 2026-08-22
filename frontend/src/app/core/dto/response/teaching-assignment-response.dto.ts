import { SubjectResponse } from './subject-response.dto';
import { TeacherResponse } from './teacher-response.dto';

export interface TeachingAssignmentResponse {
  id: number;
  classroom: string;
  subject: SubjectResponse;
  teacher: TeacherResponse;
}