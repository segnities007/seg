# Data Model

## User

- id
- name
- age
- icon
- user_info_id

## UserInfo

- id
- is_prime
- posts
- followers
- create_at
- update_at

## Follower

- id
- follower_id
- followee_id

## Post

- id
- user_id
- description
- hashtags

## Hashtag

- id
- name
