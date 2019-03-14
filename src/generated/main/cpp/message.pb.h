// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

#ifndef PROTOBUF_message_2eproto__INCLUDED
#define PROTOBUF_message_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3005001 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/generated_enum_reflection.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace protobuf_message_2eproto {
// Internal implementation detail -- do not use these members.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[3];
  static const ::google::protobuf::internal::FieldMetadata field_metadata[];
  static const ::google::protobuf::internal::SerializationTable serialization_table[];
  static const ::google::protobuf::uint32 offsets[];
};
void AddDescriptors();
void InitDefaultsCourseImpl();
void InitDefaultsCourse();
void InitDefaultsStudent_PhoneNumberImpl();
void InitDefaultsStudent_PhoneNumber();
void InitDefaultsStudentImpl();
void InitDefaultsStudent();
inline void InitDefaults() {
  InitDefaultsCourse();
  InitDefaultsStudent_PhoneNumber();
  InitDefaultsStudent();
}
}  // namespace protobuf_message_2eproto
namespace baeldung {
class Course;
class CourseDefaultTypeInternal;
extern CourseDefaultTypeInternal _Course_default_instance_;
class Student;
class StudentDefaultTypeInternal;
extern StudentDefaultTypeInternal _Student_default_instance_;
class Student_PhoneNumber;
class Student_PhoneNumberDefaultTypeInternal;
extern Student_PhoneNumberDefaultTypeInternal _Student_PhoneNumber_default_instance_;
}  // namespace baeldung
namespace baeldung {

enum Student_PhoneType {
  Student_PhoneType_MOBILE = 0,
  Student_PhoneType_LANDLINE = 1,
  Student_PhoneType_Student_PhoneType_INT_MIN_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32min,
  Student_PhoneType_Student_PhoneType_INT_MAX_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32max
};
bool Student_PhoneType_IsValid(int value);
const Student_PhoneType Student_PhoneType_PhoneType_MIN = Student_PhoneType_MOBILE;
const Student_PhoneType Student_PhoneType_PhoneType_MAX = Student_PhoneType_LANDLINE;
const int Student_PhoneType_PhoneType_ARRAYSIZE = Student_PhoneType_PhoneType_MAX + 1;

const ::google::protobuf::EnumDescriptor* Student_PhoneType_descriptor();
inline const ::std::string& Student_PhoneType_Name(Student_PhoneType value) {
  return ::google::protobuf::internal::NameOfEnum(
    Student_PhoneType_descriptor(), value);
}
inline bool Student_PhoneType_Parse(
    const ::std::string& name, Student_PhoneType* value) {
  return ::google::protobuf::internal::ParseNamedEnum<Student_PhoneType>(
    Student_PhoneType_descriptor(), name, value);
}
// ===================================================================

class Course : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:baeldung.Course) */ {
 public:
  Course();
  virtual ~Course();

  Course(const Course& from);

  inline Course& operator=(const Course& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  Course(Course&& from) noexcept
    : Course() {
    *this = ::std::move(from);
  }

  inline Course& operator=(Course&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const Course& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const Course* internal_default_instance() {
    return reinterpret_cast<const Course*>(
               &_Course_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    0;

  void Swap(Course* other);
  friend void swap(Course& a, Course& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline Course* New() const PROTOBUF_FINAL { return New(NULL); }

  Course* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const Course& from);
  void MergeFrom(const Course& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(Course* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // repeated .baeldung.Student student = 5;
  int student_size() const;
  void clear_student();
  static const int kStudentFieldNumber = 5;
  const ::baeldung::Student& student(int index) const;
  ::baeldung::Student* mutable_student(int index);
  ::baeldung::Student* add_student();
  ::google::protobuf::RepeatedPtrField< ::baeldung::Student >*
      mutable_student();
  const ::google::protobuf::RepeatedPtrField< ::baeldung::Student >&
      student() const;

  // bytes imageBytes = 2;
  void clear_imagebytes();
  static const int kImageBytesFieldNumber = 2;
  const ::std::string& imagebytes() const;
  void set_imagebytes(const ::std::string& value);
  #if LANG_CXX11
  void set_imagebytes(::std::string&& value);
  #endif
  void set_imagebytes(const char* value);
  void set_imagebytes(const void* value, size_t size);
  ::std::string* mutable_imagebytes();
  ::std::string* release_imagebytes();
  void set_allocated_imagebytes(::std::string* imagebytes);

  // string course_name = 3;
  void clear_course_name();
  static const int kCourseNameFieldNumber = 3;
  const ::std::string& course_name() const;
  void set_course_name(const ::std::string& value);
  #if LANG_CXX11
  void set_course_name(::std::string&& value);
  #endif
  void set_course_name(const char* value);
  void set_course_name(const char* value, size_t size);
  ::std::string* mutable_course_name();
  ::std::string* release_course_name();
  void set_allocated_course_name(::std::string* course_name);

  // string object_uuid = 4;
  void clear_object_uuid();
  static const int kObjectUuidFieldNumber = 4;
  const ::std::string& object_uuid() const;
  void set_object_uuid(const ::std::string& value);
  #if LANG_CXX11
  void set_object_uuid(::std::string&& value);
  #endif
  void set_object_uuid(const char* value);
  void set_object_uuid(const char* value, size_t size);
  ::std::string* mutable_object_uuid();
  ::std::string* release_object_uuid();
  void set_allocated_object_uuid(::std::string* object_uuid);

  // int32 id = 1;
  void clear_id();
  static const int kIdFieldNumber = 1;
  ::google::protobuf::int32 id() const;
  void set_id(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:baeldung.Course)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::RepeatedPtrField< ::baeldung::Student > student_;
  ::google::protobuf::internal::ArenaStringPtr imagebytes_;
  ::google::protobuf::internal::ArenaStringPtr course_name_;
  ::google::protobuf::internal::ArenaStringPtr object_uuid_;
  ::google::protobuf::int32 id_;
  mutable int _cached_size_;
  friend struct ::protobuf_message_2eproto::TableStruct;
  friend void ::protobuf_message_2eproto::InitDefaultsCourseImpl();
};
// -------------------------------------------------------------------

class Student_PhoneNumber : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:baeldung.Student.PhoneNumber) */ {
 public:
  Student_PhoneNumber();
  virtual ~Student_PhoneNumber();

  Student_PhoneNumber(const Student_PhoneNumber& from);

  inline Student_PhoneNumber& operator=(const Student_PhoneNumber& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  Student_PhoneNumber(Student_PhoneNumber&& from) noexcept
    : Student_PhoneNumber() {
    *this = ::std::move(from);
  }

  inline Student_PhoneNumber& operator=(Student_PhoneNumber&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const Student_PhoneNumber& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const Student_PhoneNumber* internal_default_instance() {
    return reinterpret_cast<const Student_PhoneNumber*>(
               &_Student_PhoneNumber_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    1;

  void Swap(Student_PhoneNumber* other);
  friend void swap(Student_PhoneNumber& a, Student_PhoneNumber& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline Student_PhoneNumber* New() const PROTOBUF_FINAL { return New(NULL); }

  Student_PhoneNumber* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const Student_PhoneNumber& from);
  void MergeFrom(const Student_PhoneNumber& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(Student_PhoneNumber* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // string number = 1;
  void clear_number();
  static const int kNumberFieldNumber = 1;
  const ::std::string& number() const;
  void set_number(const ::std::string& value);
  #if LANG_CXX11
  void set_number(::std::string&& value);
  #endif
  void set_number(const char* value);
  void set_number(const char* value, size_t size);
  ::std::string* mutable_number();
  ::std::string* release_number();
  void set_allocated_number(::std::string* number);

  // .baeldung.Student.PhoneType type = 2;
  void clear_type();
  static const int kTypeFieldNumber = 2;
  ::baeldung::Student_PhoneType type() const;
  void set_type(::baeldung::Student_PhoneType value);

  // @@protoc_insertion_point(class_scope:baeldung.Student.PhoneNumber)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr number_;
  int type_;
  mutable int _cached_size_;
  friend struct ::protobuf_message_2eproto::TableStruct;
  friend void ::protobuf_message_2eproto::InitDefaultsStudent_PhoneNumberImpl();
};
// -------------------------------------------------------------------

class Student : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:baeldung.Student) */ {
 public:
  Student();
  virtual ~Student();

  Student(const Student& from);

  inline Student& operator=(const Student& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  Student(Student&& from) noexcept
    : Student() {
    *this = ::std::move(from);
  }

  inline Student& operator=(Student&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const Student& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const Student* internal_default_instance() {
    return reinterpret_cast<const Student*>(
               &_Student_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    2;

  void Swap(Student* other);
  friend void swap(Student& a, Student& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline Student* New() const PROTOBUF_FINAL { return New(NULL); }

  Student* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const Student& from);
  void MergeFrom(const Student& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(Student* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  typedef Student_PhoneNumber PhoneNumber;

  typedef Student_PhoneType PhoneType;
  static const PhoneType MOBILE =
    Student_PhoneType_MOBILE;
  static const PhoneType LANDLINE =
    Student_PhoneType_LANDLINE;
  static inline bool PhoneType_IsValid(int value) {
    return Student_PhoneType_IsValid(value);
  }
  static const PhoneType PhoneType_MIN =
    Student_PhoneType_PhoneType_MIN;
  static const PhoneType PhoneType_MAX =
    Student_PhoneType_PhoneType_MAX;
  static const int PhoneType_ARRAYSIZE =
    Student_PhoneType_PhoneType_ARRAYSIZE;
  static inline const ::google::protobuf::EnumDescriptor*
  PhoneType_descriptor() {
    return Student_PhoneType_descriptor();
  }
  static inline const ::std::string& PhoneType_Name(PhoneType value) {
    return Student_PhoneType_Name(value);
  }
  static inline bool PhoneType_Parse(const ::std::string& name,
      PhoneType* value) {
    return Student_PhoneType_Parse(name, value);
  }

  // accessors -------------------------------------------------------

  // repeated .baeldung.Student.PhoneNumber phone = 5;
  int phone_size() const;
  void clear_phone();
  static const int kPhoneFieldNumber = 5;
  const ::baeldung::Student_PhoneNumber& phone(int index) const;
  ::baeldung::Student_PhoneNumber* mutable_phone(int index);
  ::baeldung::Student_PhoneNumber* add_phone();
  ::google::protobuf::RepeatedPtrField< ::baeldung::Student_PhoneNumber >*
      mutable_phone();
  const ::google::protobuf::RepeatedPtrField< ::baeldung::Student_PhoneNumber >&
      phone() const;

  // string first_name = 2;
  void clear_first_name();
  static const int kFirstNameFieldNumber = 2;
  const ::std::string& first_name() const;
  void set_first_name(const ::std::string& value);
  #if LANG_CXX11
  void set_first_name(::std::string&& value);
  #endif
  void set_first_name(const char* value);
  void set_first_name(const char* value, size_t size);
  ::std::string* mutable_first_name();
  ::std::string* release_first_name();
  void set_allocated_first_name(::std::string* first_name);

  // string last_name = 3;
  void clear_last_name();
  static const int kLastNameFieldNumber = 3;
  const ::std::string& last_name() const;
  void set_last_name(const ::std::string& value);
  #if LANG_CXX11
  void set_last_name(::std::string&& value);
  #endif
  void set_last_name(const char* value);
  void set_last_name(const char* value, size_t size);
  ::std::string* mutable_last_name();
  ::std::string* release_last_name();
  void set_allocated_last_name(::std::string* last_name);

  // string email = 4;
  void clear_email();
  static const int kEmailFieldNumber = 4;
  const ::std::string& email() const;
  void set_email(const ::std::string& value);
  #if LANG_CXX11
  void set_email(::std::string&& value);
  #endif
  void set_email(const char* value);
  void set_email(const char* value, size_t size);
  ::std::string* mutable_email();
  ::std::string* release_email();
  void set_allocated_email(::std::string* email);

  // int32 id = 1;
  void clear_id();
  static const int kIdFieldNumber = 1;
  ::google::protobuf::int32 id() const;
  void set_id(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:baeldung.Student)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::RepeatedPtrField< ::baeldung::Student_PhoneNumber > phone_;
  ::google::protobuf::internal::ArenaStringPtr first_name_;
  ::google::protobuf::internal::ArenaStringPtr last_name_;
  ::google::protobuf::internal::ArenaStringPtr email_;
  ::google::protobuf::int32 id_;
  mutable int _cached_size_;
  friend struct ::protobuf_message_2eproto::TableStruct;
  friend void ::protobuf_message_2eproto::InitDefaultsStudentImpl();
};
// ===================================================================


// ===================================================================

#ifdef __GNUC__
  #pragma GCC diagnostic push
  #pragma GCC diagnostic ignored "-Wstrict-aliasing"
#endif  // __GNUC__
// Course

// int32 id = 1;
inline void Course::clear_id() {
  id_ = 0;
}
inline ::google::protobuf::int32 Course::id() const {
  // @@protoc_insertion_point(field_get:baeldung.Course.id)
  return id_;
}
inline void Course::set_id(::google::protobuf::int32 value) {
  
  id_ = value;
  // @@protoc_insertion_point(field_set:baeldung.Course.id)
}

// bytes imageBytes = 2;
inline void Course::clear_imagebytes() {
  imagebytes_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Course::imagebytes() const {
  // @@protoc_insertion_point(field_get:baeldung.Course.imageBytes)
  return imagebytes_.GetNoArena();
}
inline void Course::set_imagebytes(const ::std::string& value) {
  
  imagebytes_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Course.imageBytes)
}
#if LANG_CXX11
inline void Course::set_imagebytes(::std::string&& value) {
  
  imagebytes_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Course.imageBytes)
}
#endif
inline void Course::set_imagebytes(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  imagebytes_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Course.imageBytes)
}
inline void Course::set_imagebytes(const void* value, size_t size) {
  
  imagebytes_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Course.imageBytes)
}
inline ::std::string* Course::mutable_imagebytes() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Course.imageBytes)
  return imagebytes_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Course::release_imagebytes() {
  // @@protoc_insertion_point(field_release:baeldung.Course.imageBytes)
  
  return imagebytes_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Course::set_allocated_imagebytes(::std::string* imagebytes) {
  if (imagebytes != NULL) {
    
  } else {
    
  }
  imagebytes_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), imagebytes);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Course.imageBytes)
}

// string course_name = 3;
inline void Course::clear_course_name() {
  course_name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Course::course_name() const {
  // @@protoc_insertion_point(field_get:baeldung.Course.course_name)
  return course_name_.GetNoArena();
}
inline void Course::set_course_name(const ::std::string& value) {
  
  course_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Course.course_name)
}
#if LANG_CXX11
inline void Course::set_course_name(::std::string&& value) {
  
  course_name_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Course.course_name)
}
#endif
inline void Course::set_course_name(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  course_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Course.course_name)
}
inline void Course::set_course_name(const char* value, size_t size) {
  
  course_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Course.course_name)
}
inline ::std::string* Course::mutable_course_name() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Course.course_name)
  return course_name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Course::release_course_name() {
  // @@protoc_insertion_point(field_release:baeldung.Course.course_name)
  
  return course_name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Course::set_allocated_course_name(::std::string* course_name) {
  if (course_name != NULL) {
    
  } else {
    
  }
  course_name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), course_name);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Course.course_name)
}

// string object_uuid = 4;
inline void Course::clear_object_uuid() {
  object_uuid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Course::object_uuid() const {
  // @@protoc_insertion_point(field_get:baeldung.Course.object_uuid)
  return object_uuid_.GetNoArena();
}
inline void Course::set_object_uuid(const ::std::string& value) {
  
  object_uuid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Course.object_uuid)
}
#if LANG_CXX11
inline void Course::set_object_uuid(::std::string&& value) {
  
  object_uuid_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Course.object_uuid)
}
#endif
inline void Course::set_object_uuid(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  object_uuid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Course.object_uuid)
}
inline void Course::set_object_uuid(const char* value, size_t size) {
  
  object_uuid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Course.object_uuid)
}
inline ::std::string* Course::mutable_object_uuid() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Course.object_uuid)
  return object_uuid_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Course::release_object_uuid() {
  // @@protoc_insertion_point(field_release:baeldung.Course.object_uuid)
  
  return object_uuid_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Course::set_allocated_object_uuid(::std::string* object_uuid) {
  if (object_uuid != NULL) {
    
  } else {
    
  }
  object_uuid_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), object_uuid);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Course.object_uuid)
}

// repeated .baeldung.Student student = 5;
inline int Course::student_size() const {
  return student_.size();
}
inline void Course::clear_student() {
  student_.Clear();
}
inline const ::baeldung::Student& Course::student(int index) const {
  // @@protoc_insertion_point(field_get:baeldung.Course.student)
  return student_.Get(index);
}
inline ::baeldung::Student* Course::mutable_student(int index) {
  // @@protoc_insertion_point(field_mutable:baeldung.Course.student)
  return student_.Mutable(index);
}
inline ::baeldung::Student* Course::add_student() {
  // @@protoc_insertion_point(field_add:baeldung.Course.student)
  return student_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::baeldung::Student >*
Course::mutable_student() {
  // @@protoc_insertion_point(field_mutable_list:baeldung.Course.student)
  return &student_;
}
inline const ::google::protobuf::RepeatedPtrField< ::baeldung::Student >&
Course::student() const {
  // @@protoc_insertion_point(field_list:baeldung.Course.student)
  return student_;
}

// -------------------------------------------------------------------

// Student_PhoneNumber

// string number = 1;
inline void Student_PhoneNumber::clear_number() {
  number_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Student_PhoneNumber::number() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.PhoneNumber.number)
  return number_.GetNoArena();
}
inline void Student_PhoneNumber::set_number(const ::std::string& value) {
  
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Student.PhoneNumber.number)
}
#if LANG_CXX11
inline void Student_PhoneNumber::set_number(::std::string&& value) {
  
  number_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Student.PhoneNumber.number)
}
#endif
inline void Student_PhoneNumber::set_number(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Student.PhoneNumber.number)
}
inline void Student_PhoneNumber::set_number(const char* value, size_t size) {
  
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Student.PhoneNumber.number)
}
inline ::std::string* Student_PhoneNumber::mutable_number() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Student.PhoneNumber.number)
  return number_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Student_PhoneNumber::release_number() {
  // @@protoc_insertion_point(field_release:baeldung.Student.PhoneNumber.number)
  
  return number_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Student_PhoneNumber::set_allocated_number(::std::string* number) {
  if (number != NULL) {
    
  } else {
    
  }
  number_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), number);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Student.PhoneNumber.number)
}

// .baeldung.Student.PhoneType type = 2;
inline void Student_PhoneNumber::clear_type() {
  type_ = 0;
}
inline ::baeldung::Student_PhoneType Student_PhoneNumber::type() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.PhoneNumber.type)
  return static_cast< ::baeldung::Student_PhoneType >(type_);
}
inline void Student_PhoneNumber::set_type(::baeldung::Student_PhoneType value) {
  
  type_ = value;
  // @@protoc_insertion_point(field_set:baeldung.Student.PhoneNumber.type)
}

// -------------------------------------------------------------------

// Student

// int32 id = 1;
inline void Student::clear_id() {
  id_ = 0;
}
inline ::google::protobuf::int32 Student::id() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.id)
  return id_;
}
inline void Student::set_id(::google::protobuf::int32 value) {
  
  id_ = value;
  // @@protoc_insertion_point(field_set:baeldung.Student.id)
}

// string first_name = 2;
inline void Student::clear_first_name() {
  first_name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Student::first_name() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.first_name)
  return first_name_.GetNoArena();
}
inline void Student::set_first_name(const ::std::string& value) {
  
  first_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Student.first_name)
}
#if LANG_CXX11
inline void Student::set_first_name(::std::string&& value) {
  
  first_name_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Student.first_name)
}
#endif
inline void Student::set_first_name(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  first_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Student.first_name)
}
inline void Student::set_first_name(const char* value, size_t size) {
  
  first_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Student.first_name)
}
inline ::std::string* Student::mutable_first_name() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Student.first_name)
  return first_name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Student::release_first_name() {
  // @@protoc_insertion_point(field_release:baeldung.Student.first_name)
  
  return first_name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Student::set_allocated_first_name(::std::string* first_name) {
  if (first_name != NULL) {
    
  } else {
    
  }
  first_name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), first_name);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Student.first_name)
}

// string last_name = 3;
inline void Student::clear_last_name() {
  last_name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Student::last_name() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.last_name)
  return last_name_.GetNoArena();
}
inline void Student::set_last_name(const ::std::string& value) {
  
  last_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Student.last_name)
}
#if LANG_CXX11
inline void Student::set_last_name(::std::string&& value) {
  
  last_name_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Student.last_name)
}
#endif
inline void Student::set_last_name(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  last_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Student.last_name)
}
inline void Student::set_last_name(const char* value, size_t size) {
  
  last_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Student.last_name)
}
inline ::std::string* Student::mutable_last_name() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Student.last_name)
  return last_name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Student::release_last_name() {
  // @@protoc_insertion_point(field_release:baeldung.Student.last_name)
  
  return last_name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Student::set_allocated_last_name(::std::string* last_name) {
  if (last_name != NULL) {
    
  } else {
    
  }
  last_name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), last_name);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Student.last_name)
}

// string email = 4;
inline void Student::clear_email() {
  email_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Student::email() const {
  // @@protoc_insertion_point(field_get:baeldung.Student.email)
  return email_.GetNoArena();
}
inline void Student::set_email(const ::std::string& value) {
  
  email_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:baeldung.Student.email)
}
#if LANG_CXX11
inline void Student::set_email(::std::string&& value) {
  
  email_.SetNoArena(
    &::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::move(value));
  // @@protoc_insertion_point(field_set_rvalue:baeldung.Student.email)
}
#endif
inline void Student::set_email(const char* value) {
  GOOGLE_DCHECK(value != NULL);
  
  email_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:baeldung.Student.email)
}
inline void Student::set_email(const char* value, size_t size) {
  
  email_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:baeldung.Student.email)
}
inline ::std::string* Student::mutable_email() {
  
  // @@protoc_insertion_point(field_mutable:baeldung.Student.email)
  return email_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Student::release_email() {
  // @@protoc_insertion_point(field_release:baeldung.Student.email)
  
  return email_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Student::set_allocated_email(::std::string* email) {
  if (email != NULL) {
    
  } else {
    
  }
  email_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), email);
  // @@protoc_insertion_point(field_set_allocated:baeldung.Student.email)
}

// repeated .baeldung.Student.PhoneNumber phone = 5;
inline int Student::phone_size() const {
  return phone_.size();
}
inline void Student::clear_phone() {
  phone_.Clear();
}
inline const ::baeldung::Student_PhoneNumber& Student::phone(int index) const {
  // @@protoc_insertion_point(field_get:baeldung.Student.phone)
  return phone_.Get(index);
}
inline ::baeldung::Student_PhoneNumber* Student::mutable_phone(int index) {
  // @@protoc_insertion_point(field_mutable:baeldung.Student.phone)
  return phone_.Mutable(index);
}
inline ::baeldung::Student_PhoneNumber* Student::add_phone() {
  // @@protoc_insertion_point(field_add:baeldung.Student.phone)
  return phone_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::baeldung::Student_PhoneNumber >*
Student::mutable_phone() {
  // @@protoc_insertion_point(field_mutable_list:baeldung.Student.phone)
  return &phone_;
}
inline const ::google::protobuf::RepeatedPtrField< ::baeldung::Student_PhoneNumber >&
Student::phone() const {
  // @@protoc_insertion_point(field_list:baeldung.Student.phone)
  return phone_;
}

#ifdef __GNUC__
  #pragma GCC diagnostic pop
#endif  // __GNUC__
// -------------------------------------------------------------------

// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace baeldung

namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::baeldung::Student_PhoneType> : ::google::protobuf::internal::true_type {};
template <>
inline const EnumDescriptor* GetEnumDescriptor< ::baeldung::Student_PhoneType>() {
  return ::baeldung::Student_PhoneType_descriptor();
}

}  // namespace protobuf
}  // namespace google

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_message_2eproto__INCLUDED