# Bunny King — WOS battle-report evidence register (2026-10-13)

Only Whiteout Survival sources. These are **research leads**, not independently validated battle formulas or copied datasets. Do not download/rehost third-party report images without permission.

## Public sources
- https://www.reddit.com/r/whiteoutsurvival/comments/1t0ztgu/ — two reportedly comparable fights with different results; commenters suggest lancer chance-skill timing. Hypothesis, not proven.
- https://www.reddit.com/r/whiteoutsurvival/comments/1kq5t90/ — discussion of skill trigger counts, marksman losses and hero differences. Community interpretations are unverified.
- https://www.reddit.com/r/whiteoutsurvival/comments/1svul8w/ — hero/ratio matchup discussion; not a controlled experiment.
- https://www.reddit.com/r/whiteoutsurvival/comments/1rlbczh/ — shows why full report pages, attacker/defender, context and casualty panels are needed.
- https://www.reddit.com/r/whiteoutsurvival/comments/1wqgcy6/ — community battle report reader/tester request, including high-level gear and charms; request permission before reusing samples.
- https://wolfcommand.ca/gaming-and-strategy/strategy/whiteout-survival/report/ — documented 7–11 screenshot report workflow and reconciliation against header totals (service reports reader offline at time checked).
- https://github.com/StrainReviews/wos-optimizer — public WOS optimizer with hero, equipment and stat source models; inspect licensing and validate numbers before reuse.

## Suggested report schema (nullable unless verified)
- source_url, source_post_id, image_hash_sha256, observed_at_utc, consent_or_public_use_basis, source_game='whiteout_survival', game_version, report_timestamp
- battle_type, attacker/defender, leader_hero_ids, joiner_hero_ids, hero_skill_levels, skill_trigger_counts
- infantry/lancer/marksman troop tiers, counts, ratio; chief_gear, charms, pet_buffs, research, experts, island, temporary_buffs
- kills, injured, losses, survivors per troop type, final_result
- extracted_field_confidence, screenshot_page_count, complete_report, header_totals_match, human_reviewed
- never infer missing numbers as facts; distinguish raw observation from model prediction.

## Import quality gates
1. Confirm source belongs to Whiteout Survival (not Kingshot).
2. Record post ID + normalized URL and perceptual/cryptographic image hashes to deduplicate reposts.
3. Do not train on comment explanations as ground truth.
4. Validate troop totals and casualty totals against screenshot headers; reject conflicting parses.
5. Tag incomplete screenshots and uncertainty; require manual review for skill proc and equipment fields.
6. Keep any private account identifiers and personal information out of public exports.
7. Compare only reports with matching battle type, troops, tier, hero configuration, and buffs; otherwise use for UI/schema testing rather than formula fitting.

## Next implementation
Build opt-in local screenshot batch import -> OCR -> editable confirmation -> structured local records -> evidence-linked comparisons; prioritize WOS game-specific labels, numeric consistency and deduplication. No credentials required for manual local import.
