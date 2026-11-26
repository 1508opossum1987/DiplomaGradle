package org.example.diplomagradle.datamodel.entity;

import jakarta.persistence.*;
import org.example.diplomagradle.datamodel.entity.users.Role;

//сущность для просмотра блоков
@Entity
@Table(name = "t_block_view")
public class BlockView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    //связь с ролью
    @ManyToOne
    @JoinColumn(name="f_role_id", nullable = false)
    private Role role;

    //связь с блоком
    @ManyToOne
    @JoinColumn(name="f_block_id", nullable = false)
    private Block block;

    public BlockView(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
