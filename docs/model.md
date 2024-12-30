# データベース設計ドキュメント

## データモデル

### User

- `id` String(UUID)
- `name` String
- `birthday` Int
- `icon` String
- `user_info_id` Int

### UserInfo

- `id` Int
- `is_prime` Boolean
- `posts` List<Int>
- `followers` List<Int>
- `create_at` TIMESTAMP
- `update_at` TIMESTAMP

### Follower

- `id` Int
- `follower_id` String(UUID)
- `followee_id` String(UUID)

### Post

- `id` Int
- `user_id` String(UUID)
- `description` String
- `hashtags` Int
- `create_at` TIMESTAMP
- `update_at` TIMESTAMP

### Hashtag

- `id` Int
- `name` String
- `icon` String

